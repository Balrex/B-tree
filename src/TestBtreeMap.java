import java.util.ArrayList;

public class TestBtreeMap {
    public static void main(String[] args) {
        BtreeMap<Integer, String> test_Btree = new BtreeMap<>(3);
        test_Btree.AddEl(3, "Addel");
        test_Btree.AddEl(5, "Back");
        test_Btree.AddEl(1, "Lily");
        test_Btree.AddEl(9, "Janny");
        test_Btree.AddEl(8, "Sam");
        test_Btree.AddEl(2, "Madam");
        test_Btree.AddEl(6, "Jerry");
        test_Btree.AddEl(10, "John");
        test_Btree.AddEl(52, "Igor");
        test_Btree.AddEl(17, "Boby");
        test_Btree.AddEl(24, "Marta");
        test_Btree.FindEl(5);
        test_Btree.ChangeEl(5, "Giny");
        test_Btree.FindEl(5);
        System.out.println("------------------------");
        test_Btree.remove(24);
        test_Btree.remove(52);
        test_Btree.remove(9);
        test_Btree.FindEl(9);
        test_Btree.print(8);
    }
}
