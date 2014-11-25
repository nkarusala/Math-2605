/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PowerMethod;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import math2605.MatrixMethods;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author nkaru_000
 */
public class power_method {
    
    public static power_object power_method(RealMatrix A, RealMatrix v, double epsilon, int N) {
        RealMatrix uInit = v;
        double k = 0; 
        double prevK = 0;
        int num = 0;
        double accuracy;
        for (int i = N; i > 0; i--) {
            RealMatrix uN = A.multiply(uInit);
            k = uN.getEntry(0, 0);
            for (int j = 0; j < uN.getRowDimension(); j++) {
                uN.setEntry(j, 0, uN.getEntry(j, 0) / k);
            }
            uInit = uN;
            
            accuracy = Math.abs(k - prevK);
            if (accuracy <= epsilon) {
                break;
            }
            
            if (accuracy > epsilon && i == 1) {
                return null;
            }
            
            prevK = k;
            num++;
        }
        double eVal = k;
        RealMatrix eVec = new Array2DRowRealMatrix(uInit.getRowDimension(), uInit.getColumnDimension());

        eVec.setColumnMatrix(0, uInit.getColumnMatrix(0));
        power_object retVal = new power_object(eVal, eVec, num);
        return retVal;
    }
    
    public static void main(String[] args) {
        //////////////////////////////////////////////////////
        // Edit vals to contain values for matrix A         //
        // Edit vals2 to contain values for initial vector  //
        //////////////////////////////////////////////////////
        double[][] vals = {{3, 4}, {3, 1}};
        RealMatrix A = new Array2DRowRealMatrix(vals);
        double[][] vals2 = {{1}, {1}};
        RealMatrix u = new Array2DRowRealMatrix(vals2);
        power_object a = power_method(A, u, .1, 7);
        
        List<RealMatrix> matrices = genMatrices();
        List<trace_det> trace_dets = new ArrayList<>();
        double trace;
        double det;
        int iterA;
        int iterInverseA;
        for (RealMatrix r : matrices) {
            MatrixMethods m = new MatrixMethods(r);
            RealMatrix inverseR = m.inverseMatrix();
            power_object largestVal = power_method(r, u, .00005, 100);
            power_object smallestVal = power_method(inverseR, u, .00005, 100);
            if (largestVal == null || smallestVal == null) {
                continue;
            }
            trace = m.trace();
            det = m.determinant();
            iterA = largestVal.getNumN();
            iterInverseA = smallestVal.getNumN();
            trace_det td = new trace_det(trace, det, iterA, iterInverseA);
            trace_dets.add(td);
        }
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Trace vs. Determinant for Power Method",
                "Determinant",
                "Trace",
                createDataSetA(trace_dets),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = chart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.BLUE );
        renderer.setSeriesPaint( 2 , Color.GREEN );
        renderer.setSeriesPaint( 3 , Color.BLACK );
        renderer.setSeriesPaint( 4 , Color.YELLOW );
        renderer.setSeriesPaint( 5 , Color.PINK );
        renderer.setSeriesPaint( 6 , Color.ORANGE );
        renderer.setSeriesPaint( 7 , Color.GRAY );
        renderer.setSeriesPaint( 8 , Color.MAGENTA );
        renderer.setSeriesPaint( 9 , Color.LIGHT_GRAY );
        renderer.setSeriesPaint( 10 , Color.DARK_GRAY);
        //renderer.setSeriesStroke( 0 , new BasicStroke( 3.0f ) );
        //renderer.setSeriesStroke( 1 , new BasicStroke( 2.0f ) );
        plot.setRenderer(renderer); 
        ChartFrame frame = new ChartFrame("Power Method", chart);
        frame.pack();
        frame.setVisible(true);
        
        JFreeChart inverseChart = ChartFactory.createXYLineChart(
                "Trace vs. Determinant for Inverse Power Method",
                "Determinant",
                "Trace",
                createDataSetAInverse(trace_dets),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel inverseChartPanel = new ChartPanel(inverseChart);
        inverseChartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot inversePlot = inverseChart.getXYPlot( );
        XYLineAndShapeRenderer inverseRenderer = new XYLineAndShapeRenderer( );
        inverseRenderer.setSeriesPaint( 0 , Color.RED );
        inverseRenderer.setSeriesPaint( 1 , Color.BLUE );
        inverseRenderer.setSeriesPaint( 2 , Color.GREEN );
        inverseRenderer.setSeriesPaint( 3 , Color.BLACK );
        inverseRenderer.setSeriesPaint( 4 , Color.YELLOW );
        inverseRenderer.setSeriesPaint( 5 , Color.PINK );
        inverseRenderer.setSeriesPaint( 6 , Color.ORANGE );
        inverseRenderer.setSeriesPaint( 7 , Color.GRAY );
        inverseRenderer.setSeriesPaint( 8 , Color.MAGENTA );
        inverseRenderer.setSeriesPaint( 9 , Color.LIGHT_GRAY );
        inverseRenderer.setSeriesPaint( 10 , Color.DARK_GRAY);
        inversePlot.setRenderer(renderer); 
        ChartFrame inverseFrame = new ChartFrame("Power Method", inverseChart);
        inverseFrame.pack();
        inverseFrame.setVisible(true);
    }
    
    private static XYDataset createDataSetA(List<trace_det> list) {
        final XYSeries data1 = new XYSeries("1");
        final XYSeries data2 = new XYSeries("2");
        final XYSeries data3 = new XYSeries("3");
        final XYSeries data4 = new XYSeries("4");
        final XYSeries data5 = new XYSeries("5");
        final XYSeries data6 = new XYSeries("6");
        final XYSeries data7 = new XYSeries("7");
        final XYSeries data8 = new XYSeries("8");
        final XYSeries data9 = new XYSeries("9");
        final XYSeries data10 = new XYSeries("10");
        double det;
        double trace;
        int n;
        //System.out.println("length of list: " + list.size());
        for (trace_det t : list) {
            det = t.getDet();
            trace = t.getTrace();
            n = t.getIterA();
            //System.out.println(n);
            if (n >= 1 && n <= 10) {
                //System.out.println("1-10");
                data1.add(det, trace);
            } else if (n >= 11 && n <= 20) {
                //System.out.println("11-20");
                data2.add(det, trace);
            } else if (n >= 21 && n <= 30) {
                //System.out.println("21-30");
                data3.add(det, trace);
            } else if (n >= 31 && n <= 40) {
                //System.out.println("31-40");
                data4.add(det, trace);
            } else if (n >= 41 && n <= 50) {
                //System.out.println("41-50");
                data5.add(det, trace);
            } else if (n >= 51 && n <= 60) {
                //System.out.println("51-60");
                data6.add(det, trace);
            } else if (n >= 61 && n <= 70) {
                //System.out.println("61-70");
                data7.add(det, trace);
            } else if (n >= 71 && n <= 80) {
                data8.add(det, trace);
            } else if (n >= 81 && n <= 90) {
                data9.add(det, trace);
            } else {
                data10.add(det, trace);
            }
        }
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries(data1);
        dataset.addSeries(data2);
        dataset.addSeries(data3);
        dataset.addSeries(data4);
        dataset.addSeries(data5);
        dataset.addSeries(data6);
        dataset.addSeries(data7);
        dataset.addSeries(data8);
        dataset.addSeries(data9);
        dataset.addSeries(data10);
        return dataset;
    }
    
    private static XYDataset createDataSetAInverse(List<trace_det> list) {
        final XYSeries data1 = new XYSeries("1 - 10");
        final XYSeries data2 = new XYSeries("11 - 20");
        final XYSeries data3 = new XYSeries("21 - 30");
        final XYSeries data4 = new XYSeries("31 - 40");
        final XYSeries data5 = new XYSeries("41 - 50");
        final XYSeries data6 = new XYSeries("51 - 60");
        final XYSeries data7 = new XYSeries("61 - 70");
        final XYSeries data8 = new XYSeries("71 - 80");
        final XYSeries data9 = new XYSeries("81 - 90");
        final XYSeries data10 = new XYSeries("91 - 100");
        double det;
        double trace;
        int n;
        //System.out.println("length of list: " + list.size());
        for (trace_det t : list) {
            det = t.getDet();
            trace = t.getTrace();
            n = t.getIterInverseA();
            //System.out.println(n);
            if (n >= 1 && n <= 10) {
                //System.out.println("1-10");
                data1.add(det, trace);
            } else if (n >= 11 && n <= 20) {
                //System.out.println("11-20");
                data2.add(det, trace);
            } else if (n >= 21 && n <= 30) {
                //System.out.println("21-30");
                data3.add(det, trace);
            } else if (n >= 31 && n <= 40) {
                //System.out.println("31-40");
                data4.add(det, trace);
            } else if (n >= 41 && n <= 50) {
                //System.out.println("41-50");
                data5.add(det, trace);
            } else if (n >= 51 && n <= 60) {
                //System.out.println("51-60");
                data6.add(det, trace);
            } else if (n >= 61 && n <= 70) {
                //System.out.println("61-70");
                data7.add(det, trace);
            } else if (n >= 71 && n <= 80) {
                data8.add(det, trace);
            } else if (n >= 81 && n <= 90) {
                data9.add(det, trace);
            } else {
                data10.add(det, trace);
            }
        }
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries(data1);
        dataset.addSeries(data2);
        dataset.addSeries(data3);
        dataset.addSeries(data4);
        dataset.addSeries(data5);
        dataset.addSeries(data6);
        dataset.addSeries(data7);
        dataset.addSeries(data8);
        dataset.addSeries(data9);
        dataset.addSeries(data10);
        return dataset;
    }
    
    public static List<RealMatrix> genMatrices() {
        double end = 2;
        double start = -2;
        List<RealMatrix> matrices = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            RealMatrix a = new Array2DRowRealMatrix(2, 2);
            a.setEntry(0, 0, rand.nextDouble() * (end - start) + start);
            a.setEntry(0, 1, rand.nextDouble() * (end - start) + start);
            a.setEntry(1, 0, rand.nextDouble() * (end - start) + start);
            a.setEntry(1, 1, rand.nextDouble() * (end - start) + start);
            matrices.add(a);
        }
        return matrices;
    }
}
