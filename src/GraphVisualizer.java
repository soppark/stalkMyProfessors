import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GraphVisualizer opens a simple Swing window and draws a graph.
 *
 * Each professor is shown as a blue circle.
 * Each coauthor relationship is shown as a gray line.
 */
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
        int circleRadius = Math.min(width, height) / 3;

        int n = names.size();
        int[] x = new int[n];
        int[] y = new int[n];

        // Put the professors around a circle.
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x[i] = centerX + (int)(circleRadius * Math.cos(angle));
            y[i] = centerY + (int)(circleRadius * Math.sin(angle));
        }

        // Draw coauthor lines first so circles appear on top.
        g.setColor(Color.GRAY);
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            g.drawLine(x[a], y[a], x[b], y[b]);
        }

        // Draw professor circles and names.
        for (int i = 0; i < n; i++) {
            g.setColor(new Color(135, 180, 255));
            g.fillOval(x[i] - 5, y[i] - 5, 10, 10);

            g.setColor(Color.BLACK);
            g.drawOval(x[i] - 5, y[i] - 5, 10, 10);
            g.drawString(names.get(i), x[i] - 35, y[i] - 25);
        }
    }

    /**
     * Opens the graph window.
     */
    public static void showGraph(ArrayList<String> names, ArrayList<int[]> edges) {
        JFrame frame = new JFrame("Professor Coauthor Graph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new GraphVisualizer(names, edges));
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
