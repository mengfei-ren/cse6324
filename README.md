# RUGRAT_LAMBDA
Course Project for CSE6324 Fall18

This project is a course project for CSE6324 Fall18 at UTA. The project will add Lambda Expessions to RUGRAT framework to generate Lambda Expressions in the test programs for program analysis tools.

How to run?
----------------------------------------------------

  1. Double click RUGRAT_LAMBDA.jar and it will open a window for user to input properties.
  2. Input desired benchmark properties. If user want to generate lambda expressions, just type 'YES' in the corresponding field. Most fields have tooltips.
  3. Click 'Generate' to generates desired test programs. All the java programs generated will be saved in 'TestPrograms' directory.
  4. User can also load properties from an existing configuration file. 
  5. User can save the current properties to a XML file for future generation.

Know issue(s):
----------------------------------------------------

  
  It has the same issue as the original RUGRAT tool that 'Total lines of code' property is just an upper bound of LOC instead of the real LOC in generated test programs.
  
For any other information, please contact:
----------------------------------------------------


Kasaghattaramachandra, Nagendra Prasad <nagendra.ramachandra@mavs.uta.edu>

Panda, Saroj K <saroj.panda@mavs.uta.edu>

Ren, Mengfei <mengfei.ren@mavs.uta.edu>

References
----------------------------------------------------
[RUGRAT: Evaluating program analysis and testing tools and compilers with large generated random benchmark applications.](http://ranger.uta.edu/~csallner/papers/hussain16rugrat.pdf) Software: Practice and Experience. 2016
