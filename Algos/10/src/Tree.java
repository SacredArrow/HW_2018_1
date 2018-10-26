public class Tree {
    int left_bound;
    int right_bound;
    Tree left;
    Tree right;
    Coordinates value;

    public Tree(int left_bound, int right_bound) {
        this.left_bound = left_bound;
        this.right_bound = right_bound;
    }

    public void create_tree(int left_bound, int right_bound) {
        if (left_bound == right_bound) {
            this.value = Main.ress[left_bound - 1];
        } else {
            int middle = left_bound + (right_bound - left_bound + 1) / 2 - 1;
            left = new Tree(left_bound, middle);
            right = new Tree(middle + 1, right_bound);
            Thread t1 = new Thread(() -> left.create_tree(left_bound, middle));
            Thread t2 = new Thread(() -> right.create_tree(middle + 1, right_bound));
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public Coordinates upsweep() {
        if (value != null) {
            return value;
        } else {
            class Upsweep implements Runnable {
                Tree tree;
                Coordinates return_value;

                public Upsweep(Tree tree) {
                    this.tree = tree;
                }

                @Override
                public void run() {
                    return_value = tree.upsweep();
                }
            }
            Upsweep run1 = new Upsweep(left);
            Upsweep run2 = new Upsweep(right);
            Thread t1 = new Thread(run1);
            Thread t2 = new Thread(run2);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            value = Run.sumPairs(run1.return_value, run2.return_value);
            return value;
        }
    }

}


