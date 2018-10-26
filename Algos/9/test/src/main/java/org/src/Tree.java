package org.src;
public class Tree {
    Tree left;
    Tree right;
    Integer value;
    int left_bound;
    int right_bound;

    public Tree(int left_bound, int right_bound) {
        this.left_bound = left_bound;
        this.right_bound = right_bound;
    }

    @Override
    public String toString() {
        if (left != null && value != null) {
            return ("Tree{ " + left.toString() + " " + (value != null ? value.toString() : "") + " " + right.toString() + " }");
        } else if (left != null) {
            return ("Tree{ " + left.toString() + right.toString() + " }");
        } else {
            return value.toString();
        }
    }

    public void create_tree(int left_bound, int right_bound) {
        if (left_bound == right_bound) {
            this.value = Main.prefixes[left_bound - 1];
        } else {
            int middle = left_bound + (right_bound - left_bound + 1) / 2 - 1;
            left = new Tree(left_bound, middle);
            right = new Tree(middle + 1, right_bound);
            Thread t1 = new Thread(() -> left.create_tree( left_bound, middle));
            Thread t2 = new Thread(() -> right.create_tree( middle + 1, right_bound));
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

    public int upsweep() {
        if (value != null) {
            return value;
        } else {
            class Upsweep implements Runnable {
                Tree tree;
                Integer return_value;

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

            value = run1.return_value+run2.return_value;
            return value;
        }
    }

    public void downsweep(int car) {
        value = car;
        if (left_bound == right_bound) {
            Main.prefixes[left_bound - 1] = value;
            return;
        }
        Integer calculated_value = value+left.value;
        Thread t1 = new Thread(() -> left.downsweep(value));
        Thread t2 = new Thread(() -> right.downsweep(calculated_value));
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
