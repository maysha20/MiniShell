import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GraphPlotter extends JPanel {

    private String expression;
    private List<Double> xValues;
    private List<Double> yValues;
    private double zoom = 20.0;
    private int offsetX = 0;
    private int offsetY = 0;
    private Point dragStart;

    public GraphPlotter(String expression) {
        this.expression = expression;
        this.xValues = new ArrayList<>();
        this.yValues = new ArrayList<>();
        generateData();

        // Mouse listeners for pan
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point dragEnd = e.getPoint();
                offsetX += dragEnd.x - dragStart.x;
                offsetY += dragEnd.y - dragStart.y;
                dragStart = dragEnd;
                repaint();
            }
        });

        // Zoom with mouse wheel
        addMouseWheelListener(e -> {
            zoom *= (e.getPreciseWheelRotation() > 0) ? 0.9 : 1.1;
            repaint();
        });
    }

    private void generateData() {
        xValues.clear();
        yValues.clear();
        Expression e = new ExpressionBuilder(expression).variable("x").build();
        for (double x = -100; x <= 100; x += 0.1) {
            try {
                double y = e.setVariable("x", x).evaluate();
                xValues.add(x);
                yValues.add(y);
            } catch (Exception ex) {
                xValues.add(x);
                yValues.add(Double.NaN);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraph((Graphics2D) g);
    }

    private void drawGraph(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        int originX = width / 2 + offsetX;
        int originY = height / 2 + offsetY;

        // رسم الخلفية
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);

        // رسم الشبكة
        g2.setColor(Color.LIGHT_GRAY);
        for (int x = originX % (int) zoom; x < width; x += zoom) {
            g2.drawLine(x, 0, x, height);
        }
        for (int y = originY % (int) zoom; y < height; y += zoom) {
            g2.drawLine(0, y, width, y);
        }

        // المحاور
        g2.setColor(Color.BLACK);
        g2.drawLine(0, originY, width, originY); // X-axis
        g2.drawLine(originX, 0, originX, height); // Y-axis

        // الأرقام على المحور X
        g2.setColor(Color.DARK_GRAY);
        for (int x = -width / 2; x < width / 2; x += zoom) {
            int xPos = originX + x;
            if (xPos >= 0 && xPos <= width) {
                int label = (int) Math.round((x) / zoom);
                if (label != 0)
                    g2.drawString(String.valueOf(label), xPos - 5, originY + 15);
            }
        }

        // الأرقام على المحور Y
        for (int y = -height / 2; y < height / 2; y += zoom) {
            int yPos = originY - y;
            if (yPos >= 0 && yPos <= height) {
                int label = (int) Math.round((y) / zoom);
                if (label != 0)
                    g2.drawString(String.valueOf(label), originX + 5, yPos + 5);
            }
        }

        // رسم المنحنى
        g2.setColor(Color.BLUE);
        for (int i = 1; i < xValues.size(); i++) {
            double x1Val = xValues.get(i - 1);
            double y1Val = yValues.get(i - 1);
            double x2Val = xValues.get(i);
            double y2Val = yValues.get(i);

            if (!Double.isNaN(y1Val) && !Double.isNaN(y2Val)) {
                int x1 = (int) (originX + x1Val * zoom);
                int y1 = (int) (originY - y1Val * zoom);
                int x2 = (int) (originX + x2Val * zoom);
                int y2 = (int) (originY - y2Val * zoom);

                g2.drawLine(x1, y1, x2, y2);
            }
        }

        // إبراز القمم والقيعان والجذور
        g2.setColor(Color.RED);
        for (int i = 1; i < yValues.size() - 1; i++) {
            double yPrev = yValues.get(i - 1);
            double yCurr = yValues.get(i);
            double yNext = yValues.get(i + 1);
            double xVal = xValues.get(i);

            boolean isPeak = (yCurr > yPrev && yCurr > yNext);
            boolean isValley = (yCurr < yPrev && yCurr < yNext);
            boolean isXAxisIntersection = Math.abs(yCurr) < 0.01;

            if (!Double.isNaN(yCurr) && (isPeak || isValley || isXAxisIntersection)) {
                int xPixel = (int) (originX + xVal * zoom);
                int yPixel = (int) (originY - yCurr * zoom);
                String label = String.format("(%.2f, %.2f)", xVal, yCurr);

                g2.drawString(label, xPixel + 4, yPixel - 4);
                g2.fillOval(xPixel - 2, yPixel - 2, 5, 5);
            }
        }
    }

    public static void showGraph(String expression) {
        JFrame frame = new JFrame("Graph of: " + expression);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 800);
        frame.add(new GraphPlotter(expression));
        frame.setVisible(true);
    }
}