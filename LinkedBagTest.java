/**
 * A test of LinkedBag methods union, intersection, and difference
 * 
 */
public class LinkedBagTest 
{
    public static void main(String[] args)
    {
        //create an empty bag, bag1
        BagInterface<Integer> bag1 = new LinkedBag<Integer>();
        //add 0-4 to bag1
        for (int i = 0; i < 5; i++)
            bag1.add(i);

        //create an empty bag
        BagInterface<Integer> bag2 = new LinkedBag<Integer>();
        //add 3-9 to bag2
        for (int i = 3; i<10;i++)
            bag2.add(i);
        
        //print out contents of bag1 and bag2
        System.out.println("Bag 1 = " + bag1.toArray() + "\nBag 2 =" + bag2.toArray() + "\n");

        //print union, intersection, and difference of bag 1 and bag 2
        System.out.println("Union of bag 1 and bag 2: " + bag1.union(bag2).toArray());
        System.out.println("Intersection of bag 1 and bag 2: " + ((BagInterface<Integer>) bag1).intersection(bag2).toArray());
        System.out.println("Difference between bag 1 and bag 2: " + bag1.difference(bag2).toArray());
    } //end main
}
