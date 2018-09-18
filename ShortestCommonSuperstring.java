package test;

import java.util.ArrayList;
import java.util.List;

public class ShortestCommonSuperstring
{
    private static String findShortestCommonString(List<String> strList)
    {
        // have a copy of the original list
        List<String> temps = new ArrayList<String>(strList);
        for (;;)
        {
            if (temps != null && temps.size() > 1)
            {
                int maxOverlap = 0;
                int mergeIndex = 0;
                boolean currentFirst = true;

                // compare adjacent fragments in 4 possibilities, and record maxOverlap and index
                // of maxOverlap
                for (int i = 0; i < temps.size() - 1; i++)
                {
                    String current = temps.get(i);
                    String next = temps.get(i + 1);

                    if (current.contains(next))
                    {// 1
                        if (next.length() > maxOverlap)
                        {
                            mergeIndex = i;
                            maxOverlap = next.length();
                        }

                    }
                    else if (next.contains(current))
                    {// 2
                        if (current.length() > maxOverlap)
                        {
                            mergeIndex = i;
                            maxOverlap = current.length();
                        }
                    }
                    else
                    {
                        // if the fragments are partially overlapped
                        int minSize = Math.min(current.length(), next.length());
                        for (int j = 0; j < minSize; j++)
                        {

                            if (current.startsWith(next.substring(next.length() - minSize + j, next.length())))
                            {// 3
                             // find the max overlapped one from size of minSize substring to size of 1 and
                             // break the loop
                                if ((minSize - j) > maxOverlap)
                                {
                                    maxOverlap = minSize - j;
                                    mergeIndex = i;
                                    currentFirst = false;
                                }
                                break;// just need the max overlap, break to skip minor ones

                            }
                            else if (current.endsWith(next.substring(0, minSize - j)))
                            {// 4

                                if ((minSize - j) > maxOverlap)
                                {
                                    maxOverlap = minSize - j;
                                    mergeIndex = i;
                                    currentFirst = true;
                                }
                                break;// just need the max overlap, break to skip minor ones
                            }
                        }
                    }
                }

                // after comparison, merge the max overlapped fragments based on index and
                // maxOverLap number of strings recorded in comparison
                String merged = "";

                if (temps.get(mergeIndex).contains(temps.get(mergeIndex + 1)))
                {
                    merged = temps.get(mergeIndex);
                }
                else if ((temps.get(mergeIndex + 1).contains(temps.get(mergeIndex))))
                {
                    merged = temps.get(mergeIndex + 1);
                }
                else
                {
                    if (currentFirst)
                    {
                        merged = temps.get(mergeIndex) + temps.get(mergeIndex + 1).substring(maxOverlap);
                    }
                    else
                    {
                        merged = temps.get(mergeIndex + 1) + temps.get(mergeIndex).substring(maxOverlap);
                    }
                }
                temps.remove(mergeIndex);
                temps.set(mergeIndex, merged);
            }
            else
            {
                return temps.get(0);
            }
        }
    }
    
    public static void main(String... args)
    {
        List<String> fragments = new ArrayList<String>();

        // example in doc
        fragments.add("all is well");
        fragments.add("ell that en");
        fragments.add("hat end");
        fragments.add("t ends well");

        // fragments.add("abcdefg");
        // fragments.add("aeeabc");
        // fragments.add("eabc");
        // fragments.add("fgiii");

        System.out.println(findShortestCommonString(fragments));
    }
}