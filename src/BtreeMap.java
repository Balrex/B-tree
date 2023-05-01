public class BtreeMap <T1 extends Comparable<T1>, T2> implements Comparable<T1>{
    private work head=null;
    private int t, count;

    public BtreeMap(int t){
        this.t=t;
    }

    //добавление пары в дерево
    public void AddEl(T1 key, T2 data) {
        if (head == null) {                                  // если дерево пусто
            work tmpWork = new work(null, t);
            tmpWork.addPair(key, data);
            this.head = tmpWork;
        } else {
            if (head.countChildren == 0) {                   // если дерево не пусто, но у корня пока нет потомков
                if (head.countKey + 1 <= 2 * t - 1) {              // если число пар в корне не будет превышать порог
                    head.addPair(key, data);
                } else {
                    head = head.RebuildeTree(head, 0);
                    RecursiveFindPosision(head, key, data, true);
                }
            } else {                                        // если у кроня есть потомки
                RecursiveFindPosision(head, key, data, true);
            }
        }
    }
    private void RecursiveFindPosision (work head, T1 key, T2 data, boolean marker){
        if (head != null) {
            if (head.countChildren != 0) {
                int index = head.countKey;
                for (int i = 0; i < head.countKey; ++i) {
                    node tmpNode = (node) head.pairs.get(i);
                    if (key.compareTo((T1) tmpNode.key) < 0)
                        index = i;
                }
                RecursiveFindPosision((work) head.children.get(index), key, data, marker);
            } else {
                if (marker) {
                    if (head.countKey + 1 <= 2 * t - 1)
                        head.addPair(key, data);
                    else {
                        head = head.RebuildeTree(head, 0);
                        int index = head.countKey;
                        for (int i = 0; i < head.countKey; ++i) {
                            node tmpNode = (node) head.pairs.get(i);
                            if (key.compareTo((T1) tmpNode.key) < 0)
                                index = i;
                        }
                        RecursiveFindPosision((work) head.children.get(index), key, data, true);
                    }
                } else {
                    for (int i = 0; i < head.countKey; ++i) {
                        node tmpNode = (node) head.pairs.get(i);
                        if (key.compareTo((T1) tmpNode.key) == 0) {
                            if (data == null)
                                System.out.println("В дереве под ключом " + key + " хранится: " + tmpNode.data);
                            else {
                                head.pairs.set(i, new node(key, data));
                                System.out.println("Элемент по ключу "+key+" заменён.");
                            }
                            marker = true;
                        }
                    }
                    if (!marker)
                        System.out.println("В дереве нет такого ключа!");
                }
            }
        }else
            System.out.println("Дерево пусто.");
    }

    public void FindEl(T1 key){                                     // происк элемента по ключу
        RecursiveFindPosision(head, key, null, false);
    }
    public void ChangeEl(T1 key, T2 data){
        RecursiveFindPosision(head, key, data, false);
    }
    public void clear(){                                            // полная очистка дерева
        RecursiveClearALl(head);
        System.out.println("Дерево очищено.");
    }
    private void RecursiveClearALl(work head){
        if (head.children.size() != 0)
            for (int i=0; i<head.children.size(); ++i)
                RecursiveClearALl((work) head.children.get(i));
        head.pairs.clear();
        head.children.clear();
        head.prev = null;
        head.countKey = 0;
        head.countChildren = 0;
    }

    public void print(){                                // для ручной поузловой проверки заполненности дерева
        work tmpWork = (work) head.children.get(2);
        for (int i=0; i<tmpWork.countKey; ++i) {
            node tmpNode = (node) tmpWork.pairs.get(i);
            System.out.println(tmpNode.key);
        }
    }

    @Override
    public int compareTo(T1 o) {
        return 0;
    }
}
