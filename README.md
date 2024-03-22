## Traveling Salesman Problem (TSP) Genetic Algorithm


This Java application is an implementation of the Genetic Algorithm to solve the Traveling Salesman Problem (TSP). The TSP is a classic algorithmic problem in the field of computer science and operations research which focuses on optimization. In this problem, a salesman is given a list of cities, and must determine the shortest route that allows them to visit each city once and return to their original location.

## Overview


The provided code implements a genetic algorithm to solve the TSP. The main components of the algorithm are:


Initialization - creates an initial population of random solutions.


Selection - selects the fittest individuals to be parents of the next generation.


Crossover - combines the features of two parents to create offspring with the hope of producing a better solution.


Mutation - introduces small changes to the offspring's solution to maintain diversity within the population.


Replacement - replaces the old population with the new one.


## Classes


The main class is TSPGeneticAlgorithm.

## Individual


The Individual class represents a single solution to the TSP. Each individual has its own set of cities, a fitness value, and methods to calculate the fitness, crossover, and mutate the solution.


## TSPGeneticAlgorithm


The main class TSPGeneticAlgorithm contains the main method and is responsible for the following:


Reading the input data from a file.


Initializing the population.


Driving the genetic algorithm loop: selection, crossover, and mutation.


Reporting the progress of the generations and the best solution found.

## Usage


1. Place the city data in a file with the format:

`<number_of_cities>`
  
`<city_name_1>`
  
`<city_name_2>`
  
`...`

`<city_name_n>`
  
`<cost_matrix>`


For Example

`4`

`A`

`BC`

`D`

`0 20 40 10 `

2. Compile the Java application.
3. Run the Java application, providing the file name as the input.
4. The program will output the best path found and the total cost of that path.

## Important Note

The current implementation of the crossover method can sometimes produce an invalid individual (i.e., a solution that does not visit all the cities). To avoid this issue, the crossover method returns `null` if it detects an invalid individual. A better solution is to improve the crossover method to guarantee valid individuals, but it is beyond the scope of this example.

## How to run the example

To run the example, follow these steps:

1. Save the provided Java code in a file called `TSPGeneticAlgorithm.java`.
2. Save a sample TSP problem in a text file (e.g., `tsp.txt`).
3. Open a terminal or command prompt.
4. Navigate to the directory where you saved the `TSPGeneticAlgorithm.java` file.
5. Compile the Java application:

