# Call Center
## Strategy to implement
I considered lots of strategies, the main were: using java core with one thread, using spring boot and save everything 
in a database and use multithreading approach. The first approach can implement the task while processing a call 
in a time, which I did not want. Second doesn't represent a real-world problem, as system would ask database each time 
for free employees as this is a) costly b) doesn't make sense as telephone lines do not work as a database.
Multithreading approach was chosen to imitate streaming system. With such approach multiple employees handle calls,
and each employee is blocked when he/she answers a call.

## Entities
1. Abstract employee class: all employees extend that class having name (for simplicity here - id) and
   status (free or not). Each employee can handle a call.
2. Respondent, Manager and Director: 3 types of employee. 
3. Call: has an id, rank (0 - for respondent, 1 - manager, 2 - director) and duration of a call. In this application rank
   is generated randomly (to imitate the system where people are calling different employees). Duration is used for the
   time when employee is blocked (whole duration of a call).
4. CallHandler: connects employee and a call. This entity implements Runnable  and processes call (sets status of 
   employee to busy, blocks employee from answering other calls, sets status back to free and polls queue).  
   

GetEmloyeeTypeFactory was made to differentiate between employees (factory design patter).

## Where magic happens
Main: I have 3 lists of employees: one for each type. After filling the lists, you can call. Employee will handle 
the call based on the rank of a question. If rank is 0, respondent is busy, and other employees are busy too, call will  
be placed in queue. Poll queue function handles calls in a queue. When all queues are empty, observer will get a message. 
You also get a message when a call is processed and by whom.

