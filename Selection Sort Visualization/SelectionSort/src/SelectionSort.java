import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class SelectionSort extends Canvas implements Runnable {
    static final int width = 800, height = 400;

    static Random rand = new Random();
    static int[] values = new int[width];
    int i=0;

    public static void main(String[] args) {
        SelectionSort selectionSort = new SelectionSort();
        JFrame frame = new JFrame("Selection Sort Visualization");
        frame.add(selectionSort);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Thread(selectionSort).start();

        for(int i=0; i< width; i++){
            values[i] = rand.nextInt(height);
        }
    }

    public void swap(int[] values, int a, int b){
        int temp = values[a];
        values[a] = values[b];
        values[b] = temp;
    }

    public SelectionSort(){
        this.setPreferredSize(new Dimension(width,height));
    }

    public void update(){
        if(i < values.length-1){
        	int min = i;
            for(int j=i+1;j<values.length;j++){
                if(values[j] < values[min]){
                    min = j;
                }
            }
            swap(values, i, min);
        }
        i++;
    }


    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,width,height);

        g.setColor(Color.white);
        for(int i=0; i<values.length; i++){
            g.drawLine(i,height,i,height - values[i]);
        }

        g.dispose();
        bs.show();
    }

    @Override
    public void run() {
        while(true){
            update();
            render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
