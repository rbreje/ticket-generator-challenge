# Ticket Generator Challenge

A small challenge that involves building a [Bingo 90](https://en.wikipedia.org/wiki/Bingo_(United_Kingdom)) ticket generator.

**Requirements:**

* Generate a strip of 6 tickets
  - Tickets are created as strips of 6, because this allows every number from 1 to 90 to appear across all 6 tickets. If they buy a full strip of six it means that players are guaranteed to mark off a number every time a number is called.
* A bingo ticket consists of 9 columns and 3 rows.
* Each ticket row contains five numbers and four blank spaces
* Each ticket column consists of one, two or three numbers and never three blanks.
  - The first column contains numbers from 1 to 9 (only nine),
  - The second column numbers from 10 to 19 (ten), the third, 20 to 29 and so on up until
  - The last column, which contains numbers from 80 to 90 (eleven).
* Numbers in the ticket columns are ordered from top to bottom (ASC).
* There can be **no duplicate** numbers between 1 and 90 **in the strip** (since you generate 6 tickets with 15 numbers each)

**Please make sure you add unit tests to verify the above conditions and an output to view the strips generated (command line is ok).**

Try to also think about the performance aspects of your solution. How long does it take to generate 10k strips? 
The recommended time is less than 1s (with a lightweight random implementation)

-----

## Notes

## Design 

### Second Approach

We need to start from another perspective. Hence, we start from the total amount of available numbers. Hence, the algorithm looks like follow:

1. Have a randomized list of 90 numbers
2. Split them into 9 lists which represents the columns of the tickets (1-9, 10-19, ..., 80-90)
3. Create 6 tickets and fill all columns with a numbers to ensure that we don't have 3 blanks in any column
4. Hence, 54 numbers are used, and we have 36 numbers left
5. First 3 tickets will get 6 more numbers from the pool (keeping maximum 3 per column)
6. The 4th ticket will get at lest 1 number from 9th column if there are still more than 4 numbers left. Otherwise, it will follow the same approach as before.
7. The 5th ticket must get as below. After conditions are met, it will follow the same approach as before.
  - 2 numbers from the groups which have 4 numbers left
  - 1 number from the groups which have 3 numbers left
8. The 6th ticket will get all the remaining numbers
9. We need to sort every column to ensure an ascending order of the numbers
10. We need to fill the columns with bank spaces:
  - 4 random non-full columns will get a zero at the top
  - all columns with only 1 number will get 2 zeros at the middle and bottom
  - random columns with 2 numbers will get zeros keeping 4 zeros per row
  - all remaining columns with 2 numbers (including zero) will get a zero at the bottom
11. Validate the tickets

### First Approach (Outdated)

I tried to generate the ticket individually, but it's not scalable. 

- implement a component that generates a ticket
- implement a component that generates a strip of tickets
- implement a component that validates a strip of tickets
- implement a simple REST API to generate a strip of tickets

## TODOs

[ ] Enhance the validation service readability
[ ] Enhance the generation service readability
[ ] Add tests for all the service
[ ] Implement the REST endpoint to generate a ticket
[ ] Convert the domain to DTO as JSON