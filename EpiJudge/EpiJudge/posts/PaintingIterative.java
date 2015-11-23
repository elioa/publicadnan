/*
    @slug
    paint-matrix

    @title
    Painting a Boolean matrix

    @problem
    Let A be a Boolean 2D array encoding a black-and-white image. The entry
    A(a, b) can be viewed as encoding the color at location (a, b). 
    Define the region associated with an entry to be all entries
    for which there exists a path from the initial entry to those entries
    all of which are the same color.
    <p>

    Implement a routine that takes an Boolean array A together with an entry (x, y)
    and flips the color of the region associated with (x, y). See Figure 15.5 for an example
    of flipping.

    <img src="/paint-matrix.png"></img>

    @hint
    Solve this conceptually, then think about implementation optimizations.

 */

package com.epi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class PaintingIterative {
  // @include
  private static class Coordinate {
    public Integer x;
    public Integer y;

    public Coordinate(Integer x, Integer y) {
      this.x = x;
      this.y = y;
    }
  }

  // @judge-include-display
  public static void flipColor(List<List<Boolean>> A, int x, int y) {
  // @judge-exclude-display
    int[][] dir = new int[][] {new int[] {0, 1}, new int[] {0, -1},
                               new int[] {1, 0}, new int[] {-1, 0}};
    boolean color = A.get(x).get(y);

    Queue<Coordinate> q = new LinkedList<>();
    A.get(x).set(y, !A.get(x).get(y)); // Flips.
    q.add(new Coordinate(x, y));
    while (!q.isEmpty()) {
      Coordinate curr = q.element();
      for (int[] d : dir) {
        Coordinate next = new Coordinate(curr.x + d[0], curr.y + d[1]);
        if (next.x >= 0 && next.x < A.size() && next.y >= 0
            && next.y < A.get(next.x).size()
            && A.get(next.x).get(next.y) == color) {
          // Flips the color.
          A.get(next.x).set(next.y, !color);
          q.add(next);
        }
      }
      q.remove();
    }
  // @judge-include-display
  }
  // @judge-exclude-display
  // @exclude

  public static void main(String[] args) {
    int n;
    Random gen = new Random();
    if (args.length == 1) {
      n = Integer.parseInt(args[0]);
    } else {
      n = gen.nextInt(100) + 1;
    }

    List<List<Boolean>> A = new ArrayList<>(n);
    for (int i = 0; i < n; ++i) {
      A.add(new ArrayList(n));
      for (int j = 0; j < n; ++j) {
        A.get(i).add(gen.nextBoolean());
      }
    }
    int i = gen.nextInt(n), j = gen.nextInt(n);
    System.out.println("color = " + i + " " + j + " " + A.get(i).get(j));
    System.out.println(A);
    flipColor(A, i, j);
    System.out.println(A);
  }
}
