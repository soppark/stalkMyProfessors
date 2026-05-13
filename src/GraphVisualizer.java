import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphVisualizer extends JPanel {
    private ArrayList<String> names;
    private ArrayList<int[]> edges;

    public GraphVisualizer(ArrayList<String> names, ArrayList<int[]> edges) {
        this.names = names;
        this.edges = edges;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3;

        int n = names.size();
        int[] x = new int[n];
        int[] y = new int[n];

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x[i] = centerX + (int) (radius * Math.cos(angle));
            y[i] = centerY + (int) (radius * Math.sin(angle));
        }

        g.setColor(Color.GRAY);
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            g.drawLine(x[from], y[from], x[to], y[to]);
        }

        for (int i = 0; i < n; i++) {
            g.setColor(new Color(135, 180, 255));
            g.fillOval(x[i] - 20, y[i] - 20, 40, 40);

            g.setColor(Color.BLACK);
            g.drawOval(x[i] - 20, y[i] - 20, 40, 40);
            g.drawString(names.get(i), x[i] - 25, y[i] - 25);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        names.add("Carol");
        names.add("David");
        names.add("Eva");

        ArrayList<int[]> edges = new ArrayList<>();
        edges.add(new int[] {0, 1});
        edges.add(new int[] {0, 2});
        edges.add(new int[] {1, 3});
        edges.add(new int[] {2, 3});
        edges.add(new int[] {2, 4});

        JFrame frame = new JFrame("Coauthor Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GraphVisualizer(names, edges));
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
