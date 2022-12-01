public class Main {

    //generate an array of 0 and 1 randomly
    public static long[][] generate(int rows, int columns) {
        long[][] Array = new long[rows][columns];
        for (int i = 0; i < Array.length; i++)
            for (int j = 0; j < Array[i].length; j++)
                Array[i][j] = Math.round(Math.random());
        return Array;
    }

    public static void main(String[] args) {

        long[][] a = generate(400, 100);
        long[][] b = generate(100, 300);
        long[][] c = generate(300, 120);
        long[][] d = generate(120, 200);
        long[][] e = generate(200, 700);
        long[][] f = generate(700, 100);
        long[][] g = generate(100, 400);

        long tic1 = System.nanoTime();
        long[][] Sol1 = MCM.leftToRightMultiply(a, b, c, d, e, f, g);
        long toc1 = System.nanoTime();

        long tic2 = System.nanoTime();
        long[][] Sol2 = MCM.orderThenMultiply(a, b, c, d, e, f, g);
        long toc2 = System.nanoTime();

        System.out.println("\nMultiplication time without ordering:\t" + (toc1 - tic1) + " ns");
        System.out.println("Multiplication time with ordering:\t\t" + (toc2 - tic2) + " ns");


//        Check the solutions :
//        if(Arrays.deepEquals(Sol1, Sol2)) System.out.println("\nThe two solutions are identical");
//        System.out.println(Sol1[10][10] + "  =  " + Sol2[10][10]);


//        Solving step by step :
//        int[] Dimensions = MCM.extractDimensions(a,b,c,d,e,f,g);
//        int[][] SplitLocations = MCM.extractSplitLocation(Dimensions);
//        int[][] BracketLocations = MCM.orderedEquation(SplitLocations);
//        long[][] Sol = MCM.solveByBrackets(BracketLocations,a,b,c,d,e,f,g);

    }
}
