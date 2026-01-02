# Survey and Poll Manager

A complete tool for creating surveys, collecting ratings, and analyzing results with ASCII bar charts.

## What You'll Learn

This project teaches:

- **Arrays and ArrayLists** for storing data
- **Frequency Counting** algorithm pattern
- **Statistics** (mean, median, mode, standard deviation)
- **ASCII Visualization** creating bar charts in the terminal
- **File I/O** for data persistence
- **Data Validation** handling user input safely

## How to Run

```bash
javac RatingFrequency.java
java RatingFrequency
```

## Features

### Survey Management
- Add custom questions
- Collect multiple responses at once
- View all questions with response counts

### Data Visualization
- ASCII bar charts showing frequency distribution
- Percentage breakdown for each rating
- Proportional bar lengths

### Statistics
- **Mean** (average rating)
- **Median** (middle value)
- **Mode** (most common rating)
- **Standard Deviation** (spread of data)
- Automatic interpretation of results

### Persistence
- Save survey to file
- Auto load on startup
- Customizable rating scale

## Sample Output

```
========================================
Q: How satisfied are you with our service?
========================================

Rating | Frequency | Bar
-------+-----------+------------------------------------------
   1   |      0    |                                          (0.0%)
   2   |      1    | ######                                   (5.6%)
   3   |      3    | ##################                       (16.7%)
   4   |      7    | ##########################################  (38.9%)
   5   |      7    | ##########################################  (38.9%)

Total responses: 18
```

## Statistics Explained

### Mean (Average)
Add all ratings and divide by count. Shows overall tendency.
```
Ratings: 4, 5, 3, 5, 4
Mean = (4+5+3+5+4) / 5 = 4.2
```

### Median (Middle)
Sort all ratings and find the middle one. Less affected by outliers.
```
Sorted: 3, 4, 4, 5, 5
Median = 4 (the middle value)
```

### Mode (Most Frequent)
The rating that appears most often.
```
Ratings: 4, 5, 4, 3, 4
Mode = 4 (appears 3 times)
```

### Standard Deviation
How spread out the ratings are. Low = consistent, High = varied opinions.

## File Format

Surveys are saved in a simple text format:
```
SCALE:1,5
Q:How satisfied are you with our service?
R:4,5,3,5,4,4,5,3,4,5
Q:How likely are you to recommend us?
R:5,5,4,5,3,5,4,5
```

## Rating Scale

Default is 1 to 5, but you can change it:
- 1 to 10 for detailed ratings
- 0 to 100 for percentage based feedback
- Any range up to 10 units wide

## Try These Modifications

1. Add multiple choice questions (not just numeric scales)
2. Export results to CSV
3. Add comparison between questions
4. Implement trend analysis over time
5. Add response timestamps
6. Create a summary report generator
7. Add weighted averages
8. Implement NPS (Net Promoter Score) calculation
