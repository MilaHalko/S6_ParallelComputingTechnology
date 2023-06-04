
import mpi.MPI;

public class CollectiveMPI {
    private static final int MASTER_ID = 0;
    private static final int INT_32_BYTE_SIZE = 4;
    public static void main(String[] args) {
        int matrixSize = 1000;
        Matrix matrixA = new Matrix(matrixSize, matrixSize, true);
        Matrix matrixB = new Matrix(matrixSize, matrixSize, true);
        try{
            long startTime = System.currentTimeMillis();

            MPI.Init(args);

            int tasksCount = MPI.COMM_WORLD.Size();
            int taskID = MPI.COMM_WORLD.Rank();

            var rowsForOneWorker = matrixA.getRowsNumber() / tasksCount;
            var extraRows = matrixA.getRowsNumber() % tasksCount;

            int[] bytes = new int[tasksCount];
            for (var i = 0; i < tasksCount; i++) {
                if (i != tasksCount - 1) {
                    bytes[i] = rowsForOneWorker * matrixB.getColumnsNumber() * INT_32_BYTE_SIZE;
                } else {
                    bytes[i] = (rowsForOneWorker + extraRows) * matrixB.getColumnsNumber() * INT_32_BYTE_SIZE;
                }
            }

            int[] offsets = new int[tasksCount];
            for (var i = 0; i < offsets.length; i++) {
                if (i == 0) continue;

                offsets[i] = bytes[i - 1] + offsets[i - 1];
            }

            byte[] matrixAByteBuffer = matrixA.toByteBuffer();
            byte[] matrixBByteBuffer = matrixB.toByteBuffer();

            int taskBytes = bytes[taskID];
            byte[] subMatrixBytes = new byte[taskBytes];
            byte[] resBytes = new byte[matrixA.getRowsNumber() * matrixB.getColumnsNumber() * INT_32_BYTE_SIZE];

            MPI.COMM_WORLD.Scatterv(matrixAByteBuffer, 0, bytes, offsets, MPI.BYTE,
                    subMatrixBytes, 0, taskBytes, MPI.BYTE, 0);

            MPI.COMM_WORLD.Bcast(matrixBByteBuffer, 0, matrixBByteBuffer.length, MPI.BYTE, 0);

            byte[] multiplicationResultBuffer = performMatrixMultiplication(matrixA.getRowsNumber(),
                    matrixB.getColumnsNumber(), matrixBByteBuffer, taskBytes, subMatrixBytes)
                    .toByteBuffer();

            MPI.COMM_WORLD.Gatherv(multiplicationResultBuffer, 0, multiplicationResultBuffer.length,
                    MPI.BYTE, resBytes, 0, bytes, offsets, MPI.BYTE, 0);

            if (taskID == MASTER_ID) {
                Matrix resultMatrix = Matrix.createMatrixFromBuffer(resBytes, matrixA.getRowsNumber(),
                        matrixB.getColumnsNumber());

                System.out.println("CollectiveMPI");
                System.out.println("Total time: " + (System.currentTimeMillis() - startTime) + " ms");
                System.out.println("Matrix size: " + resultMatrix.getRowsNumber() + "x" + resultMatrix.getColumnsNumber());
                System.out.println("Tasks count: " + tasksCount);
            }
        }finally {
            MPI.Finalize();
        }
    }

    private static Matrix performMatrixMultiplication(int matrix1RowsCount, int matrix2ColumnsCount, byte[] secondMatrixBuffer, int taskBytes, byte[] subMatrixBytes) {
        Matrix subMatrix = Matrix.createMatrixFromBuffer(subMatrixBytes, taskBytes / (INT_32_BYTE_SIZE * matrix2ColumnsCount), matrix1RowsCount);
        Matrix secondMatrix = Matrix.createMatrixFromBuffer(secondMatrixBuffer, matrix2ColumnsCount, matrix1RowsCount);

        return subMatrix.multiply(secondMatrix);
    }
}