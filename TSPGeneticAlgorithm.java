import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class TSPGeneticAlgorithm {

    private static class Individual {
        private int[] cities;
        private double fitness;
        private Set<Integer> visited= new HashSet<Integer>();
        public Individual(int[] cities) {
            this.cities = cities;
            this.fitness = calculateFitness();
        }

        private double calculateFitness() {
            double fitness = 0;
            for (int i = 0; i < cities.length - 1; i++)
                fitness += costs[cities[i]][cities[i + 1]];
            return fitness;
        }

        public Individual crossover(Individual other) {
            int[] childCities = new int[cities.length];
            int crossoverPoint = new Random().nextInt(cities.length);
            for (int i = 0; i < cities.length; i++) {
                if (i < crossoverPoint) {
                    if(visited.contains(cities[i])){
                        return null;
                    }
                    else{
                        childCities[i] = cities[i];
                    }
                } else {
                    if(visited.contains(other.cities[i])){
                        return null;
                    }
                    else{
                        childCities[i] = other.cities[i];
                    }
                }
            }
            return new Individual(childCities);
        }

        public Individual mutate() {
            int[] mutatedCities = Arrays.copyOf(cities, cities.length);
            int mutationPoint1 = new Random().nextInt(cities.length);
            int mutationPoint2 = new Random().nextInt(cities.length);
            while (mutationPoint1 == mutationPoint2) {
                mutationPoint2 = new Random().nextInt(cities.length);
            }
            int temp = mutatedCities[mutationPoint1];
            mutatedCities[mutationPoint1] = mutatedCities[mutationPoint2];
            mutatedCities[mutationPoint2] = temp;
            return new Individual(mutatedCities);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < cities.length; i++) {
                sb.append(cities[i]);
                if (i < cities.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    private static int[][] costs;
    private static List<String> cityNames;
    private static int populationSize;
    private static double mutationRate;
    private static int generations;

    public static void main(String[] args) throws IOException {
        System.out.print("Please input the name of the file you want to do a TSP genetic algorithm on: ");
        Scanner input=new Scanner(System.in);
        String filename=input.next();
        try{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int N = Integer.parseInt(reader.readLine());
        cityNames = new ArrayList<>();
        costs = new int[N][N];
        for (int i = 0; i < N; i++) {
            String cityName = reader.readLine();
            cityNames.add(cityName);
            int[] cityCosts = new int[N];
            Arrays.fill(cityCosts, Integer.MAX_VALUE);
            costs[i] = cityCosts;
        }
        for (int i = 0; i < N; i++) {
            String[] line = reader.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                costs[i][j] = Integer.parseInt(line[j]);
            }
        }
        reader.close();
        input.close();

        populationSize = 2 * N;
        mutationRate = 0.05;
        generations = 100;
        System.out.println(populationSize);
        Individual[] population = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            int[] cities = new int[N];
            for (int j = 0; j < N; j++) {
                cities[j] = j;
            }
            shuffle(cities);
            population[i] = new Individual(cities);
        }
        Individual best = population[0];
        for (int i = 0; i < generations; i++) {
            Individual best2=population[0];
            for (int j = 1; j < population.length-1; j++) {
                if(!(population[j]==null)){
                    if (population[j].fitness < best.fitness) {
                        best = population[j];
                    }
                    if(population[j].fitness<best2.fitness){
                        best2=population[j];
                    }
                }
            }
            System.out.println("Generation " + i + ": " + best2.fitness);

            ArrayList<Individual> newPopulation = new ArrayList<Individual>(populationSize);
            for (int j = 0; j < populationSize / 2; j++) {
                Individual parent1 = select(population);
                Individual parent2 = select(population);
                Individual child = parent1.crossover(parent2);
                if (new Random().nextDouble() < mutationRate) {
                    child = child.mutate();
                }
                newPopulation.add(child);
            }
            population = newPopulation.toArray(new Individual[0]);
        }

        // for (Individual individual : population) {
        //     if (best == null || individual.fitness < best.fitness) {
        //         best = individual;
        //     }
        // }

        System.out.println("Best path: " + best);
        System.out.println("Best path cost: " + best.fitness);
        }
        catch(Exception e){
            System.out.println("Please input an existing file name");
        }
    }

    private static Individual select(Individual[] population) {
        double totalFitness = 0;
        for (Individual individual : population) {
            if(individual!=null){
                totalFitness += individual.fitness;
            }
        }
        double randomPoint = new Random().nextDouble() * totalFitness;
        double currentPoint = 0;
        for (int i = 0; i < population.length; i++) {
            if(population[i]!=null){
                currentPoint += population[i].fitness;
                if (currentPoint > randomPoint) {
                    return population[i];
                }
            }
        }
        return population[population.length - 1];
    }

    private static void shuffle(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}