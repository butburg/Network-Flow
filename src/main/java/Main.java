package main.java;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.PrintWriter;
        import java.util.Scanner;

public class Main {

    private static final String FILEPATH = "src/main/resources/";
    private static int verticesN;


    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            System.out.println("Wrong number of command line arguments. 2 arguments needed. \nExample: >java main.java.Main inFile.txt outFile.txt");
            System.exit(1);
        }

        int[][] inputMatrix = readFile(args[0]);

        FFA ffa = new FFA(inputMatrix);

        ffa.calculate();

        StringBuilder output = new StringBuilder();



        //weight and number of moves is same as any move has the cost of one
        int maxFlow = ffa.getMaxFlow();
        int[] cut = ffa.getCut();
        int steps = ffa.getSteps();

        output.append("Max Flow: ");
        output.append(maxFlow);
        output.append("\n");
        output.append("Cut: ");
        output.append(cut);
        output.append("\n");
        output.append("Steps: ");
        output.append(steps);
        output.append("\n");
        output.append(ffa.printMatrix());

        System.out.println(output);

        writeFile(args[1], output.toString());
    }


    public static int[][] readFile(String fileInput) throws FileNotFoundException, NumberFormatException, IndexOutOfBoundsException {
        //build String Array from File
        // read file
        try (Scanner in = new Scanner(new File(FILEPATH + fileInput))) {

            verticesN = Integer.parseInt(in.nextLine());
            int[][] matrix = new int[verticesN][verticesN];

            int i = 0;
            int j = 0;
            int lineNumber = verticesN;

            while (in.hasNextLine() && lineNumber > 0) {
                // read numbers per line
                String row = in.nextLine();
                Scanner scRow = new Scanner(row);
                while (scRow.hasNextInt()) {
                    int digit = scRow.nextInt();
                    matrix[j][i] = digit;
                    i++;
                }
                i = 0;
                scRow.close();
                j++;
                lineNumber--;
            }
            in.close();

            if ((verticesN != matrix.length) || (verticesN != matrix[0].length)) {
                System.out.println("Number N ist not equal to the Dimension of the given Matrix!");
                System.exit(1);
            }

            return matrix;
        }
    }

    public static void writeFile(String fileOutput, String output) throws FileNotFoundException {
        //output the results into a file:
        try (PrintWriter out = new PrintWriter(FILEPATH + fileOutput)) {
            out.print(output);
        }
    }

}
