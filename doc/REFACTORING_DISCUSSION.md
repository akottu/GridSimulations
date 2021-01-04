## Lab Discussion
### Team 10
### James Arnold, Anish Kottu, Hosam Tageldin


### Issues in Current Code
There a couple of really long methods in a few classes. We throw a lot of printStackTraces which
we can just remove and throw our own exception. Some of our methods are too complex. We use a
few magic values throughout the program as well. Two methods have a lot of parameters.

#### Method or Class
 * Design issues
Each specific State subclass has the same implementation of the getStateIndex method.

 * Design issues
 

#### Method or Class
 * Design issues

 * Design issue


### Refactoring Plan

 * What are the code's biggest issues?
 One of the biggest issues is the duplication of the getStateIndex in each state class that implements
 the State Interface. There are also a couple of really long methods in a few classes. We throw a lot of printStackTraces which
 we can just remove and throw our own exception. Some of our methods are too complex. We use a
 few magic values throughout the program as well. Two methods have a lot of parameters.

 * Which issues are easy to fix and which are hard?
 The magic values and the really long methods will be an easy fix. Also removing the printStackTraces
 will be a quick fix. Fixes that will be more challenging include the duplication of the getStateIndex
 method and the complex expressions/methods that we currently have.

 * What are good ways to implement the changes "in place"?
 


### Refactoring Work

 * Issue chosen: Fix and Alternatives
 Fix the printStackTraces in the catch blocks and throw our own exceptions.
 We will fix this by removes these lines, condensing all the different catch blocks and throwing
 our own exception.


 * Issue chosen: Fix and Alternatives
 Fixing the duplication of the getStateIndex method in each of the State classes. 
 
 Externalizing the getStateIndex to a Util class. Since we can call .values() externally, a Util 
 class can handle the Enum state to index conversion. 
 
 