import java.util.ArrayList;

public class work<T1 extends Comparable<T1>, T2> implements Comparable<T1> {
    ArrayList<node<T1, T2>> pairs = new ArrayList<>();
    ArrayList<work<T1, T2>> children = new ArrayList<>();
    int countKey, countChildren, t;
    work<T1, T2> prev;
    public work(work prev, int t){
        this.prev=prev;
        this.t=t;
    }
    public void addPair(T1 key, T2 data){
        this.pairs = ReWritePairs(this.pairs, key, data, this.countKey);
        ++this.countKey;
    }
    private ArrayList<node<T1, T2>> ReWritePairs(ArrayList<node<T1, T2>> Arpairs, T1 key, T2 data, int count){
        boolean marker=true;
        if (count != 0) {
            for (int i = 0; i < count; ++i) {
                node tmpNode = Arpairs.get(i);
                //System.out.println(key+" * "+tmpNode.key);
                if (key.compareTo((T1) tmpNode.key) < 0) {
                    Arpairs.add(i, new node<>(key, data));
                    //System.out.println("|| "+key);
                    marker = false;
                    break;
                }
            }
        }
        if (marker) {
            pairs.add(new node(key, data));
            //System.out.println("|| "+ key);
        }
        return pairs;
    }
    public work RebuildeTree(work nowWork,int repite) {
        if (nowWork.prev != null && nowWork.prev.countKey >= 2 * t - 1)   // если этот узел не корень и у предка количество пар достигло максимального значение
            nowWork = RebuildeTree(nowWork.prev, repite+1);

        if (nowWork.prev == null) {                                          // если мы находимся в корне
            if (nowWork.countChildren == 0) {                               // и при этом у него ещё нет потомков
                int middle = nowWork.countKey / 2;
                work FirstTmpChildOfNowork = new work(nowWork, nowWork.t);
                work SecondTmpChildOfNowork = new work(nowWork, nowWork.t);
                nowWork.children.add(FirstTmpChildOfNowork); nowWork.children.add(SecondTmpChildOfNowork);

                for (int i = 0; i < nowWork.t - 1; ++i) {               // перенос в новых потомков всех пар, кроме центральной
                    node tmpNode;
                    tmpNode = (node) nowWork.pairs.get(0);
                    nowWork.pairs.remove(0);
                    FirstTmpChildOfNowork.pairs.add(tmpNode);
                    ++FirstTmpChildOfNowork.countKey;
                    tmpNode = (node) nowWork.pairs.get(middle-i);
                    nowWork.pairs.remove(middle-i);
                    SecondTmpChildOfNowork.pairs.add(tmpNode);
                    ++SecondTmpChildOfNowork.countKey;
                }

                nowWork.countKey = 1;
                nowWork.countChildren = 2;

            } else {                                                // если был достигнут предел по количесву детей у корня
                work FirstTmpChildOfNowork = new work(nowWork, nowWork.t);
                work SecondTmpChildOfNowork = new work(nowWork, nowWork.t);
                int middle = nowWork.countKey / 2;

                for (int i = 0; i < nowWork.t - 1; ++i) {                   // перенос в новых потомков всех пар, кроме центральной
                    node tmpNode;
                    tmpNode = (node) nowWork.pairs.get(0);
                    nowWork.pairs.remove(0);
                    FirstTmpChildOfNowork.pairs.add(tmpNode);
                    ++FirstTmpChildOfNowork.countKey;
                    tmpNode = (node) nowWork.pairs.get(middle-i);
                    nowWork.pairs.remove(middle-i);
                    SecondTmpChildOfNowork.pairs.add(tmpNode);
                    ++SecondTmpChildOfNowork.countKey;
                }
                for (int i=0; i<nowWork.t; ++i){                            // перенос (с разделением по полам) потомков корня в его 2 новых потомка
                    work tmpWork = (work) nowWork.children.get(i);
                    tmpWork.prev = FirstTmpChildOfNowork;
                    FirstTmpChildOfNowork.children.add(tmpWork);
                    tmpWork = (work) nowWork.children.get(nowWork.t+i);
                    tmpWork.prev = SecondTmpChildOfNowork;
                    SecondTmpChildOfNowork.children.add(tmpWork);
                }

                nowWork.children.clear();
                nowWork.children.add(FirstTmpChildOfNowork);
                nowWork.children.add(SecondTmpChildOfNowork);
                nowWork.countChildren = 2;
                nowWork.countKey = 1;

                FirstTmpChildOfNowork.countChildren = 3;
                SecondTmpChildOfNowork.countChildren = 3;
            }
        } else if (nowWork.prev.countKey < 2*t-1){                 // если мы находимся не в корне
            int middle = nowWork.countKey / 2, index = nowWork.prev.countKey;
            node tmpMiddleNode = (node) nowWork.pairs.get(middle);
            work prevWork = nowWork.prev;
            boolean marker=true;

            for (int i=0; i<prevWork.countKey; ++i) {
                T1 tmpKey = (T1) tmpMiddleNode.key;
                node tmpNode = (node) prevWork.pairs.get(i);
                if (tmpKey.compareTo((T1) tmpNode.key) < 0) {
                    prevWork.pairs.add(i, tmpMiddleNode);
                    index = i;
                    marker = false;
                }
            }
            if (marker)
                prevWork.pairs.add(tmpMiddleNode);
            prevWork.countKey++;

            work NewTmpPervChild = new work(prevWork, prevWork.t);
            prevWork.children.add(index, NewTmpPervChild);
            prevWork.countChildren++;
            for (int i=0; i<=middle; ++i){
                if (i < middle) {
                    NewTmpPervChild.pairs.add(nowWork.pairs.get(0));
                    ++NewTmpPervChild.countKey;
                }
                nowWork.pairs.remove(0);
                nowWork.countKey--;
                if (repite != 0) {
                    NewTmpPervChild.children.add(nowWork.children.get(0));
                    ++NewTmpPervChild.countChildren;
                    nowWork.children.remove(0);
                    nowWork.countChildren--;
                }
            }

            if (repite == 0)
                return prevWork;
        }

        return nowWork;
    }

    @Override
    public int compareTo(T1 o) {
        return 0;
    }
}
