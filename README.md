# Matrix Chain Multiplication

This repository contains a Java program that optimizes the order of chained matrix multiplications to minimize the number of scalar multiplications required. The program includes a method that generates matrices with random values of 0s and 1s, and allows users to specify the dimensions of these matrices. The program then compares the performance of the normal (left to right) method of matrix multiplication to the optimized method and displays the results.<br><br>

The program includes several methods that work together to solve the matrix chain multiplication problem.<br>
The `extractDimensions()` method takes in a variable number of matrices and returns an array of their dimensions.<br>
The `extractSplitLocation()` method uses the dimensions array to determine the optimal order for multiplying the matrices, and returns an array of split locations indicating where the chain should be split in order to minimize the number of multiplications.<br>
The `orderedEquation()` method then uses the split locations array to generate an equation representing the optimized multiplication order, with brackets indicating the order in which the matrices should be multiplied.<br>
Finally, the `solveByBrackets()` method uses the equation with brackets to actually perform the optimized matrix multiplication.<br><br>

To use the program, run the Main class and adjust the values for the generate() method to specify the dimensions of the matrices you want to use. The program will then generate and multiply the matrices using both the normal and optimized methods, and display the time taken and the minimum number of multiplications required for each method.
