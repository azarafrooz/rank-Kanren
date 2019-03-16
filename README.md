# rank-Kanren

This is the rKanren extension of core.logic using [rKanren paper](http://www.schemeworkshop.org/2013/papers/Swords2013.pdf)

miniKanren uses a biased interleaving search, alternating between disjuncts to unfold.
The alternation is **biased** towards disjuncts that have more of their constraints already satisfied. However, one can affect bias by parametrizing the search strategy using ranks. 

## Why rank is important?

The parametrization is beneficial if we want to take advantage of differentiable programming to guide the search strategies. Think of Deep Neural Networks that are trained to generate appropriate ranks based on a fixed given puzzle. You can play with sudoku example using the included jar file. 

## Example on how to affect the search speed of the sudoku 

```java -jar target/uberjar/minimax-kanren-0.1.0-SNAPSHOT-standalone.jar  --puzzle 192005640000060002006001300907083000000000000000590103009800200200070000085600739 --rank 54.17.58.1.66.73.71.44.64.32.20.26.40.61.79.7.41.69.37.42.31.25.11.56.14.24.29.62.27.51.57.77.50.33.76.12.52.65.45.18.9.35.19.4.16.75.48.59.46.43.21.55.5.63.78.74.23.39.67.80.13.60.34.70.36.15.6.2.28.53.47.72.68.10.8.22.3.38.49.30
```

## Running test
lein test

## Future
Stay tuned for the result/papers and the Pytorch codes for showcasing the benefit of Neural guidance in the search strategies of constraint logical programmings. 
If this idea tingled your brain, make sure to check [neural-guided-constraint-logic-programming-for-program-synthesis](https://papers.nips.cc/paper/7445-neural-guided-constraint-logic-programming-for-program-synthesis). 


