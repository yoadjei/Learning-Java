# Interactive Number Sorter & Algorithm Visualizer

A Java console application that helps you understand sorting algorithms by visualizing each step.

## What You'll Learn

This project teaches:

- **Arrays** and how to manipulate them
- **Sorting Algorithms** (Bubble, Selection, Insertion)
- **Performance Measurement** using System.nanoTime()
- **Algorithm Comparison** to understand time complexity
- **User Input** for interactive programs

## How to Run

```bash
javac DescendingOdds.java
java DescendingOdds
```

## Features

### 1. Sort Your Own Numbers
Enter numbers manually or generate random ones. Choose your algorithm and sort order.

### 2. Original Odd Numbers Demo
The classic exercise: find odd numbers 1 to 9 and sort them descending. Now with step by step visualization.

### 3. Algorithm Comparison
Race all three algorithms against each other on the same data to see which is faster.

### 4. Step by Step Mode
Toggle visualization on/off to see exactly what each algorithm does at every step.

### 5. Learn About Algorithms
Built in explanations of how each algorithm works and when to use them.

## The Algorithms

### Bubble Sort
Compares adjacent elements and swaps if out of order. Repeats until sorted.
```
[5, 3, 8, 1] 
-> compare 5,3 -> swap -> [3, 5, 8, 1]
-> compare 5,8 -> ok -> [3, 5, 8, 1]
-> compare 8,1 -> swap -> [3, 5, 1, 8]
... repeat until done
```

### Selection Sort
Finds the smallest element and puts it first. Repeats for rest of array.
```
[5, 3, 8, 1] -> find min (1) -> swap with first -> [1, 3, 8, 5]
[1, 3, 8, 5] -> find min in rest (3) -> already there -> [1, 3, 8, 5]
...
```

### Insertion Sort
Builds sorted array one element at a time by inserting each in the right spot.
```
[5, 3, 8, 1]
-> insert 3 in sorted [5] -> [3, 5, 8, 1]
-> insert 8 in sorted [3,5] -> [3, 5, 8, 1]
-> insert 1 in sorted [3,5,8] -> [1, 3, 5, 8]
```

## Sample Output

```
Pass 1: [5, 3, 8, 1, 9]
  Swapped 5 and 3 -> [3, 5, 8, 1, 9]
  Swapped 8 and 1 -> [3, 5, 1, 8, 9]
Pass 2: [3, 5, 1, 8, 9]
  Swapped 5 and 1 -> [3, 1, 5, 8, 9]
Pass 3: [3, 1, 5, 8, 9]
  Swapped 3 and 1 -> [1, 3, 5, 8, 9]
Pass 4: [1, 3, 5, 8, 9]
No swaps needed. Array is sorted!
```

## Try These Modifications

1. Add Quick Sort or Merge Sort algorithms
2. Count the number of comparisons and swaps
3. Add a visual bar graph representation
4. Implement sorting for strings instead of numbers
5. Add a mode to sort in reverse during mid process
