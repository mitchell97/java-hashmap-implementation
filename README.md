# lab-5-maps-hashing

See lab document: https://uwoece-se2205b-2017.github.io/labs/05-maps-hash


###Questions:

1. If the state of a key changes after it has been stored in the hash map, the 
key may not be able to be retrieved. If the hashcode is based on the fields of the 
key and they are different, then the hashcode will point to a different index.

2. A load factor of 0.3 is good for linear probing because you want to avoid
large clusters of entries that have collided together. The more sparse the underlying 
structure is the better the performance will be in terms of time. When doing chaining,
a higher threshold can be used because there will be less collisions on 
the same group of indices.

3. Double hashing will ideally have less collisions that linear probing because the 
hashcodes are dispersed twice. This will lead to improved performance.
