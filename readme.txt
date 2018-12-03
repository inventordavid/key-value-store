Author: WAN, Kwok Lung
Date: 2018-12-03

Interfaces:
- /src/main/java/lung/key_value_store/api/ErrorListener
- /src/main/java/lung/key_value_store/api/KeysAndValues

Implementation Class:
- /src/main/java/lung/key_value_store/KeysAndValuesImpl

Test Class:
- /src/test/java/lung/key_value_store/KeysAndValuesImplTest

Some performance notes:
- In this program, I try to generate less garbage by reusing objects.
- HashMap size is initialized with EXPECTED_NUMBER_OF_UNIQUE_KEYS to avoid
  resizing and to improve hash function distribution with less duplicated values.
- Java 8 Stream is used.
- Methods are made shorter to favour JIT compiler.
- ++i is faster than i++
- A custom method isInteger(String) to check if a String represents an integer,
  not checking by Integer.parseInt for better performance.
- Make local variables as shortcuts of object data members that are frequently
  accessed in a method to avoid frequent address redirection.