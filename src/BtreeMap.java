import java.util.ArrayList;
import java.util.List;

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
        } else
            RecursiveFindPosision(head, key, data, true, false);
    }
    private work RecursiveFindPosision (work head, T1 key, T2 data, boolean marker, boolean delMarker){
        if (head != null) {
            if (head.countChildren != 0) {
                boolean Go = true;
                int index = head.countKey;
                for (int i = 0; i < head.countKey; ++i) {
                    node tmpNode = (node) head.pairs.get(i);
                    if (key.compareTo((T1) tmpNode.key) < 0) {
                        index = i;
                        break;
                    } else if (key.compareTo((T1) tmpNode.key) == 0) {
                        Go = false;
                        if (delMarker)
                            return head;
                        if (marker)
                            System.out.println("В дереве уже есть ключ " + key + ". Пара не довлена в дерево.");
                        else if (data == null)
                            System.out.println("В дереве под ключом " + key + " хранится: " + tmpNode.data);
                        else {
                            head.pairs.set(i, new node(key, data));
                            System.out.println("Элемент по ключу " + key + " заменён.");
                        }
                        break;
                    }

                }
                if (Go) {
                    head = RecursiveFindPosision((work) head.children.get(index), key, data, marker, delMarker);
                    return head;
                }
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
                        RecursiveFindPosision((work) head.children.get(index), key, data, marker, delMarker);
                    }
                } else {
                    for (int i = 0; i < head.countKey; ++i) {
                        node tmpNode = (node) head.pairs.get(i);
                        if (key.compareTo((T1) tmpNode.key) == 0) {
                            if (delMarker) {
                                return head;
                            } else if (data == null)
                                System.out.println("В дереве под ключом " + key + " хранится: " + tmpNode.data);
                            else {
                                head.pairs.set(i, new node(key, data));
                                System.out.println("Элемент по ключу "+key+" заменён.");
                            }
                            marker = true;
                        }
                    }
                    if (!marker) {
                        System.out.println("В дереве нет такого ключа!");
                        return null;
                    }
                }
            }
        }else
            System.out.println("Дерево пусто.");

        if (!delMarker)
            return head;
        else
            return null;
    }

    public void FindEl(T1 key){                                     // происк элемента по ключу
        RecursiveFindPosision(head, key, null, false, false);
    }
    public void ChangeEl(T1 key, T2 data){                          // изменение значения по ключу
        RecursiveFindPosision(head, key, data, false, false);
    }
    public void clear(){                                            // полная очистка дерева
        RecursiveClearALl(head);
        System.out.println("Дерево очищено.");
    }
    public void remove(T1 key) {
        work delWork = RecursiveFindPosision(head, key, null, false, true);
        if (delWork != null) {                                  // если такая пара есть в дереве
            if (delWork.countChildren == 0) {                   // если эта пара расположена в листе
                if (delWork.countKey - 1 > 1)                   // если после ёё удаления в листе останется не менее 2х пар
                    for (int i = 0; i < delWork.countKey; ++i) {
                        node tmpNode = (node) delWork.pairs.get(i);
                        if (key.compareTo((T1) tmpNode.key) == 0) {
                            delWork.pairs.remove(i);
                            --delWork.countKey;
                            System.out.println("Пара по ключу "+key+" удалена из дерева.");
                            break;
                        }
                    }
                else {                                              // если в листе после удаления пары, останется только 1 пара
                    work prevWork = delWork.prev;
                    if (prevWork.countChildren -1 > 1){             // если после удаления этого листа у его предка остантся еще не менее 2х листов
                    node tmpListNode = (node) delWork.pairs.get(0), tmpPrevNode = null;
                    if (key.compareTo((T1) tmpListNode.key) == 0) {
                        delWork.pairs.remove(0);
                        tmpListNode = (node) delWork.pairs.get(0);
                        delWork.pairs.remove(0);
                        delWork.prev = null;
                        delWork.countKey =0;
                    } else {
                        delWork.pairs.remove(0);
                        delWork.pairs.remove(0);
                        delWork.prev = null;
                        delWork.countKey = 0;
                    }

                    int index = prevWork.countKey;
                    T1 tmpKey = (T1) tmpListNode.key;
                    for (int i = 0; i < prevWork.countKey; ++i) {
                        tmpPrevNode = (node) prevWork.pairs.get(i);
                        if (tmpKey.compareTo((T1) tmpPrevNode.key) < 0) {
                            index = i;
                            break;
                        }
                    }
                    if (index == prevWork.countKey)
                        prevWork.pairs.remove(index-1);
                    else
                        prevWork.pairs.remove(index);
                    --prevWork.countKey;
                    prevWork.children.remove(index);
                    --prevWork.countChildren;
                    RecursiveFindPosision(head, (T1) tmpListNode.key, (T2) tmpListNode.data, true, false);
                    RecursiveFindPosision(head, (T1) tmpPrevNode.key, (T2) tmpPrevNode.data, true, false);
                }else {                                                                // если после удаления этого листа у предка останется только 1 лист
                        ArrayList<node> tmpNode = new ArrayList<>();
                        node tmpCheckNode = (node) delWork.pairs.get(0);

                        if (key.compareTo((T1) tmpCheckNode.key) == 0) {
                            delWork.pairs.remove(0);
                            tmpNode.add((node) delWork.pairs.remove(0));
                            delWork.prev = null;
                            delWork.countKey =0;
                        } else {
                            delWork.pairs.remove(0);
                            delWork.pairs.remove(0);
                            tmpNode.add(tmpCheckNode);
                            delWork.prev = null;
                            delWork.countKey = 0;
                        }

                        T1 tmpKey = (T1) tmpCheckNode.key;
                        tmpCheckNode = (node) prevWork.pairs.get(0);
                        if (tmpKey.compareTo((T1) tmpCheckNode.key) < 0){
                            prevWork.children.remove(0);
                            --prevWork.countChildren;
                            delWork = (work) prevWork.children.get(0);
                        }else {
                            prevWork.children.remove(1);
                            --prevWork.countChildren;
                            delWork = (work) prevWork.children.get(0);
                        }

                        for (int i=0; i<delWork.countKey; ++i){
                            tmpNode.add((node) delWork.pairs.get(0));
                            delWork.pairs.remove(0);
                        }

                        delWork.prev = null;
                        delWork.countKey = 0;
                        prevWork.children.remove(0);
                        --prevWork.countChildren;

                        int size = tmpNode.size();
                        for (int i=0; i<size; ++i){
                            tmpCheckNode = tmpNode.get(0);
                            RecursiveFindPosision(head, (T1) tmpCheckNode.key, (T2) tmpCheckNode.data, true, false);
                            tmpNode.remove(0);
                        }
                    }
                }
            } else {                                                // если пара находится не в листе
                int index = delWork.countKey;
                node tmpNode;
                for (int i=0; i<delWork.countKey; ++i){
                    tmpNode = (node) delWork.pairs.get(i);
                    if (key.compareTo((T1) tmpNode.key) == 0){
                        delWork.pairs.remove(i);
                        index = i;
                        break;
                    }
                }

                work listWork = (work) delWork.children.get(index);
                if (listWork.countKey - 1 > 1){
                    tmpNode = (node) listWork.pairs.get(listWork.countKey-1);
                    listWork.pairs.remove(listWork.countKey-1);
                    --listWork.countKey;
                    delWork.pairs.add(index, tmpNode);
                }else {
                    listWork = (work) delWork.children.get(index+1);
                    if (listWork.countKey-1 > 1){
                        tmpNode = (node) listWork.pairs.get(0);
                        listWork.pairs.remove(0);
                        --listWork.countKey;
                        delWork.pairs.add(index, tmpNode);
                    }else {
                        ArrayList<node> tmpArrNode = new ArrayList<>();
                        if (delWork.countKey - 2 > 0)
                            for (int i = 0; i < delWork.countKey - 2; ++i) {
                                tmpArrNode.add((node) delWork.pairs.get(0));
                                delWork.pairs.remove(0);
                            }
                        delWork.countKey = 1;
                        int size1 = delWork.countChildren;
                        for (int i=0; i<size1; ++i){
                            work tmpWork = (work) delWork.children.get(0);
                            int size = tmpWork.countKey;
                            --delWork.countChildren;
                            delWork.children.remove(0);
                            for (int j=0; j<size; ++j){
                                tmpArrNode.add((node) tmpWork.pairs.get(0));
                                --tmpWork.countKey;
                                tmpWork.pairs.remove(0);
                            }
                        }

                        size1 = tmpArrNode.size();
                        for (int i=0; i<size1; ++i){
                            tmpNode = tmpArrNode.get(0);
                            tmpArrNode.remove(0);
                            RecursiveFindPosision(delWork, (T1) tmpNode.key, (T2) tmpNode.data, true, false);
                        }

                    }
                }
            }
        }
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

    public void print(T1 key){                                // для ручной поузловой проверки заполненности дерева
        work tmpWork = RecursiveFindPosision(head, key, null, false, true);
        //work tmpWork = (work) head.children.get(0);
        if (tmpWork != null) {
            System.out.println(tmpWork.countKey+" - "+tmpWork.countChildren);
            for (int i = 0; i < tmpWork.countKey; ++i) {
                node tmpNode = (node) tmpWork.pairs.get(i);
                System.out.println(tmpNode.key);
            }
        }
    }

    @Override
    public int compareTo(T1 o) {
        return 0;
    }
}
