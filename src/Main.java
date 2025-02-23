import java.util.*;

//demoing iterator usage across different collections
public class Main {
    public static void main(String[] args) {
        // Execute all tasks
        ArrayListIteration();
        HashMapIteration();
        TreeMapAnalysis();
    }

    //TASK 1: ArrayList Iteration and Modification
    //to demo proper and improper collection modification during iteration

    private static void ArrayListIteration() {
        System.out.println("=== TASK 1: ArrayList Iteration ===");

        // 1. Create and populate ArrayList
        ArrayList<Integer> numbers = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < 10; i++) {
            numbers.add(rand.nextInt(100));
        }
        System.out.println("Original list: " + numbers);

        // 2. Iteration with sum calculation
        Iterator<Integer> iter = numbers.iterator();
        int sum = 0;
        System.out.print("Iterating values: ");
        while(iter.hasNext()) {
            int num = iter.next();
            System.out.print(num + " ");
            sum += num;
        }
        System.out.println("\nTotal sum: " + sum);

        // 3. Modification tests
        System.out.println("\nModification Tests:");

        // Safe removal using iterator
        iter = numbers.iterator();
        while(iter.hasNext()) {
            if(iter.next() % 2 == 0) { // even numbers
                iter.remove();
            }
        }
        System.out.println("After iterator removal: " + numbers);

        // Unsafe modification attempt
        try {
            iter = numbers.iterator();
            while(iter.hasNext()) {
                int num = iter.next();
                if(num > 50) {
                    numbers.remove(Integer.valueOf(num)); // Concurrent modification
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Caught ConcurrentModificationException!");
        }
    }


    //TASK 2: HashMap Entry Iteration and Modification
    private static void HashMapIteration() {
        System.out.println("\n\n=== TASK 2: HashMap Iteration ===");

        // 1. Create and populate HashMap
        HashMap<String, Integer> categoryCounts = new HashMap<>();
        categoryCounts.put("Books", 12);
        categoryCounts.put("Movies", 8);
        categoryCounts.put("Games", 15);
        categoryCounts.put("Music", 20);
        categoryCounts.put("Apps", 5);

        // 2. Iteration through entry set
        Iterator<Map.Entry<String, Integer>> iter =
                categoryCounts.entrySet().iterator();
        System.out.println("Initial counts:");
        while(iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // 3. Modification during iteration
        iter = categoryCounts.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            if(entry.getKey().equals("Movies")) {
                categoryCounts.put("Movies", entry.getValue() + 12); // Safe value update
            }
            if(entry.getKey().equals("Apps")) {
                iter.remove(); // Safe removal
            }
        }
        System.out.println("\nModified counts:");
        System.out.println(categoryCounts);
    }


    //TASK 3: TreeMap Temperature Analysis
    private static void TreeMapAnalysis() {
        System.out.println("\n\n=== TASK 3: TreeMap Analysis ===");

        // 1. Create and populate TreeMap (natural String ordering)
        TreeMap<String, Double> temperatures = new TreeMap<>();
        temperatures.put("January", -5.2);
        temperatures.put("April", 12.8);
        temperatures.put("July", 28.5);
        temperatures.put("October", 15.3);
        temperatures.put("December", -2.1);
        temperatures.put("March", 8.4);

        // 2. Temperature analysis
        Iterator<Map.Entry<String, Double>> iter =
                temperatures.entrySet().iterator();
        double maxTemp = Double.MIN_VALUE;
        double minTemp = Double.MAX_VALUE;
        String maxMonth = "", minMonth = "";

        while(iter.hasNext()) {
            Map.Entry<String, Double> entry = iter.next();
            if(entry.getValue() > maxTemp) {
                maxTemp = entry.getValue();
                maxMonth = entry.getKey();
            }
            if(entry.getValue() < minTemp) {
                minTemp = entry.getValue();
                minMonth = entry.getKey();
            }
        }
        System.out.println("Temperature Analysis:");
        System.out.printf("Hottest: %s (%.1f°C)\n", maxMonth, maxTemp);
        System.out.printf("Coldest: %s (%.1f°C)\n", minMonth, minTemp);
        System.out.println("Natural ordering demonstration:");
        System.out.println(temperatures.keySet());
    }

    // Edge test vases
    private static void testCases() {
        System.out.println("\n\n=== TEST CASES ===");


        testEmptyArrayList();
        testNullHashMapValues();
        testTreeMapSingleEntry();
        testConcurrentModification();
    }
    // edge test cases
    private static void testEmptyArrayList() {
        ArrayList<Integer> emptyList = new ArrayList<>();
        Iterator<Integer> iter = emptyList.iterator();
        System.out.println("\nTest 1: Empty ArrayList - hasNext: " + iter.hasNext());
    }

    private static void testNullHashMapValues() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Test", null);
        Iterator<Map.Entry<String, Integer>> iter = map.entrySet().iterator();
        System.out.println("\nTest 2: HashMap with null value: " + iter.next());
    }

    private static void testTreeMapSingleEntry() {
        TreeMap<String, Double> singleMap = new TreeMap<>();
        singleMap.put("Only", 42.0);
        Iterator<Map.Entry<String, Double>> iter = singleMap.entrySet().iterator();
        System.out.println("\nTest 3: TreeMap single entry: " + iter.next());
    }

    private static void testConcurrentModification() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
        try {
            Iterator<String> iter = list.iterator();
            list.add("D");
            iter.next();
        } catch (ConcurrentModificationException e) {
            System.out.println("\nTest 4: Caught expected ConcurrentModificationException");
        }
    }
}