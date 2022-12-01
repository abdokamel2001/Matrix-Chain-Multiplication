import java.util.Arrays;

public class MCM {

    public static int[] extractDimensions(long[][]... ArrayChain) {
        int[] Dimensions = new int[ArrayChain.length + 1];
        Dimensions[0] = ArrayChain[0].length;
        Dimensions[ArrayChain.length] = ArrayChain[ArrayChain.length - 1][0].length;
        for (int i = 1; i < ArrayChain.length; i++) {
            if (ArrayChain[i].length != ArrayChain[i - 1][0].length) multiplicationError();
            Dimensions[i] = ArrayChain[i].length;
        }
        System.out.println("\nthe dimensions array: " + Arrays.toString(Dimensions));
        return Dimensions;
    }

    public static int[][] extractSplitLocation(int[] Dimensions) {
        int numOfArrays = Dimensions.length - 1;
        int[][] M = new int[numOfArrays][numOfArrays]; //
        int[][] SplitLocations = new int[numOfArrays][numOfArrays];
        for (int x = 1; x < numOfArrays; x++) {
            for (int i = 0; i < numOfArrays; i++) {
                int j = i + x;
                if (j >= numOfArrays) break;
                M[i][j] = M[i][i] + M[i + 1][j] + Dimensions[i] * Dimensions[i + 1] * Dimensions[j + 1];
                SplitLocations[i][j] = i;
                for (int k = i + 1; k < j; k++) {
                    int min = M[i][k] + M[k + 1][j] + Dimensions[i] * Dimensions[k + 1] * Dimensions[j + 1];
                    if (min < M[i][j]) {
                        M[i][j] = min;
                        SplitLocations[i][j] = k;
                    }
                }
            }
        }
        System.out.println("the minimum number of multiplications is " + M[0][numOfArrays - 1]);
        return SplitLocations;
    }

    public static int[][] orderedEquation(int[][] SplitLocations) {
        StringBuilder equation = new StringBuilder();
        int numOfArrays = SplitLocations.length;
        int[][] BracketLocations = new int[numOfArrays][2];
        placeBrackets(BracketLocations, SplitLocations, 0, numOfArrays - 1);
        simplifyBrackets(BracketLocations);
        for (int i = 0; i < numOfArrays; i++) {
            equation.append("( ".repeat(Math.max(0, BracketLocations[i][0])));
            equation.append("A").append(i);
            equation.append(" )".repeat(Math.max(0, BracketLocations[i][1])));
            if (i < numOfArrays - 1) equation.append(" * ");
        }
        System.out.println("The ordered equation = " + equation);
        return BracketLocations;
    }

    public static void placeBrackets(int[][] BracketLocations, int[][] SplitLocations, int start, int end) {
//        BracketLocations[i][0] -> (
//        BracketLocations[i][1] -> )
        int split = SplitLocations[start][end];
        if (split - start > 1) placeBrackets(BracketLocations, SplitLocations, start, split);
        BracketLocations[start][0]++;
        BracketLocations[split][1]++;
        if (end - split > 1) placeBrackets(BracketLocations, SplitLocations, split + 1, end);
        BracketLocations[split + 1][0]++;
        BracketLocations[end][1]++;
    }

    public static long[][] solveByBrackets(int[][] oldBrackets, long[][]... Arrays) {
//        Brackets[i][0] -> (
//        Brackets[i][1] -> )
        int remainingArrays = oldBrackets.length;
        if(remainingArrays == 1) return Arrays[0];
        int[][] newBrackets = new int[remainingArrays - 1][2];
        for(int i =0 ; i < remainingArrays - 1; i++){
            if(oldBrackets[i][1] == 0 && oldBrackets[i+1][0] == 0){
                for (int j = 0; j < i; j++){
                    newBrackets[j][0] = oldBrackets[j][0];
                    newBrackets[j][1] = oldBrackets[j][1];
                }
                newBrackets[i][0] = oldBrackets[i][0];
                newBrackets[i][1] = oldBrackets[i+1][1];
                Arrays[i] = singleMultiply(Arrays[i],Arrays[i+1]);
                for(int j = i + 1; j < remainingArrays - 1; j++){
                    newBrackets[j][0] = oldBrackets[j+1][0];
                    newBrackets[j][1] = oldBrackets[j+1][1];
                    Arrays[j] = Arrays[j+1];
                }
                break;
            }
        }
        simplifyBrackets(newBrackets);
        return solveByBrackets(newBrackets,Arrays);
    }

    public static long[][] singleMultiply(long[][] leftArray, long[][] rightArray) {
        if (leftArray[0].length != rightArray.length) multiplicationError();
        long[][] Result = new long[leftArray.length][rightArray[0].length];
        for (int i = 0; i < leftArray.length; i++)
            for (int j = 0; j < rightArray[0].length; j++)
                for (int k = 0; k < leftArray[0].length; k++)
                    Result[i][j] += leftArray[i][k] * rightArray[k][j];
        return Result;
    }

    public static long[][] orderThenMultiply(long[][]... Arrays){
        //ordering operation
        int[] Dimensions = MCM.extractDimensions(Arrays);
        int[][] SplitLocations = MCM.extractSplitLocation(Dimensions);
        int[][] BracketLocations = MCM.orderedEquation(SplitLocations);
        //actual multiplication
        return solveByBrackets(BracketLocations,Arrays);
    }

    public static long[][] leftToRightMultiply(long[][]... Arrays){
        long[][] Result = Arrays[0];
        for (int i = 1; i < Arrays.length; i++){
            Result = singleMultiply(Result,Arrays[i]);
        }
        return Result;
    }

    private static void simplifyBrackets(int[][] BracketLocations){
//        BracketLocations[i][0] -> (
//        BracketLocations[i][1] -> )
        for (int i = 0; i < BracketLocations.length; i++) {
            if (BracketLocations[i][0] > BracketLocations[i][1]) {
                BracketLocations[i][0] -= BracketLocations[i][1];
                BracketLocations[i][1] = 0;
            } else {
                BracketLocations[i][1] -= BracketLocations[i][0];
                BracketLocations[i][0] = 0;
            }
        }
    }

    private static void multiplicationError() {
        System.out.println("The matrices can't be multiplied");
        System.exit(0);
    }
}