package ar.edu.itba.eda.IoT;

import ar.edu.itba.eda.Queue.QueueImp;

import java.util.Scanner;
public class IoT {
    private Scanner scannerLine;

    public IoT() {
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Valores sensados (separados por blancos): ");
        inputScanner.hasNextLine();
        String line = inputScanner.nextLine();
        scannerLine = new Scanner(line).useDelimiter("\\s+");
    }

    // mientras haya tokens procesa e imprime en ventanas de tamano "windowSize"
    public void generate(int windowSize) {
        if (windowSize < 1)
            throw new RuntimeException("invalid window size");
        QueueImp<String> queue = new QueueImp<>(String.class,windowSize);
        for (int i = 0; scannerLine.hasNext() && i < windowSize; i++) {
            queue.queue(scannerLine.next());
        }
        // 10 20 30 33
        // 20 30 33 12
        // 30 33 12 15
        queue.print();
        while( scannerLine.hasNext() ) {
            queue.dequeue();
            queue.queue(scannerLine.next());
            queue.print();
        }
    }



    public static void main(String[] args) {
        //new IoT().generate(2);
        new IoT().generate(4);
    }
}