# One Billion Row Challenge – In-Memory Version

This is an optimized Java solution for the One Billion Row Challenge that reads the entire input file into memory and processes it to compute, for each weather station, the minimum, mean, and maximum temperature values.

> **Warning:**  
> This version loads the entire file into memory. For a file of around 13 GB, ensure your system has enough RAM and that you allocate a sufficiently large heap (e.g., using `-Xmx20G` or higher).

## Features

- **Entire File in Memory:**  
  Uses `Files.readAllBytes` to load the full file into a byte array.

- **Direct Byte-to-Char Conversion:**  
  Converts the byte array to a char array using ISO‑8859‑1 (1:1 mapping) for efficient processing.

- **Custom Parsing:**  
  Processes the char array in one pass by scanning for newline characters and uses a custom parser to extract station names and temperature values.

- **Station Name Caching:**  
  Caches station names to reduce duplicate String allocations.

- **Statistics Calculation:**  
  Computes minimum, mean, and maximum temperature for each weather station.

- **Timing Logs:**  
  Reports time taken for file reading, conversion, processing, and overall execution.

## Prerequisites

- **Java 11 or later** is required.
- Ensure that your system has sufficient memory to load the entire file. For a ~13 GB file, run with a large heap size:
  ```bash
  -Xmx20G

```
javac OneBillionRowChallengeInMemory.java
```

```
java -server   -Xms20G -Xmx20G   -XX:+UseG1GC   -XX:MaxGCPauseMillis=10   -XX:ParallelGCThreads=16   -XX:ConcGCThreads=11   -XX:+UseStringDeduplication    OneBillionRowChallengeInMemory sample-data.txt
Using: 11
Segments: 11
File Size: 13283069948
Processing complete in 89.065 seconds.
{Abha=-20.0/15.0/50.0, Abidjan=-20.0/15.0/50.0, AbÃ©chÃ©=-20.0/15.0/50.0, Accra=-20.0/15.0/50.0, Addis Ababa=-20.0/15.0/50.0, Adelaide=-20.0/15.0/50.0, Amsterdam=-20.0/15.0/50.0, Ankara=-20.0/15.0/50.0, Athens=-20.0/15.0/50.0, Auckland=-20.0/15.0/50.0, Bangkok=-20.0/15.0/50.0, Barcelona=-20.0/15.0/50.0, Beijing=-20.0/15.0/50.0, Belgrade=-20.0/15.0/50.0, Berlin=-20.0/15.0/50.0, BogotÃ¡=-20.0/15.0/50.0, Boston=-20.0/15.0/50.0, Brisbane=-20.0/15.0/50.0, Brussels=-20.0/15.0/50.0, Bucharest=-20.0/15.0/50.0, Budapest=-20.0/15.0/50.0, Buenos Aires=-20.0/15.0/50.0, Cairo=-20.0/15.0/50.0, Cape Town=-20.0/15.0/50.0, Casablanca=-20.0/15.0/50.0, Chicago=-20.0/15.0/50.0, Copenhagen=-20.0/15.0/50.0, Dallas=-20.0/15.0/50.0, Delhi=-20.0/15.0/50.0, Dubai=-20.0/15.0/50.0, Dublin=-20.0/15.0/50.0, Frankfurt=-20.0/15.0/50.0, Geneva=-20.0/15.0/50.0, Helsinki=-20.0/15.0/50.0, Hong Kong=-20.0/15.0/50.0, Istanbul=-20.0/15.0/50.0, Jakarta=-20.0/15.0/50.0, Johannesburg=-20.0/15.0/50.0, Kiev=-20.0/15.0/50.0, Kuala Lumpur=-20.0/15.0/50.0, Lagos=-20.0/15.0/50.0, Lima=-20.0/15.0/50.0, Lisbon=-20.0/15.0/50.0, London=-20.0/15.0/50.0, Los Angeles=-20.0/15.0/50.0, Madrid=-20.0/15.0/50.0, Manila=-20.0/15.0/50.0, Mexico City=-20.0/15.0/50.0, Milan=-20.0/15.0/50.0, Moscow=-20.0/15.0/50.0, Mumbai=-20.0/15.0/50.0, Munich=-20.0/15.0/50.0, Nairobi=-20.0/15.0/50.0, New York=-20.0/15.0/50.0, Oslo=-20.0/15.0/50.0, Paris=-20.0/15.0/50.0, Prague=-20.0/15.0/50.0, Rio de Janeiro=-20.0/15.0/50.0, Rome=-20.0/15.0/50.0, San Francisco=-20.0/15.0/50.0, Santiago=-20.0/15.0/50.0, Seoul=-20.0/15.0/50.0, Singapore=-20.0/15.0/50.0, Stockholm=-20.0/15.0/50.0, Sydney=-20.0/15.0/50.0, Taipei=-20.0/15.0/50.0, Tokyo=-20.0/15.0/50.0, Toronto=-20.0/15.0/50.0, Vienna=-20.0/15.0/50.0, Warsaw=-20.0/15.0/50.0, Zurich=-20.0/15.0/50.0}
```
