package de.outstare.fortbattleplayer.gui.statistics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.AxisTypeProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.ChartTypeProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.LineChartProperties;
import org.jCharts.properties.PropertyException;
import org.jCharts.types.ChartType;

import de.outstare.fortbattleplayer.model.CombatantSide;

/**
 * Displays a chart for a set of data. The data should have a value for each
 * round.
 * 
 * @author daniel
 */
public class StatisticDiagramm extends JComponent implements ComponentListener {
	private static final long serialVersionUID = -1302032715590574718L;
	private static final transient Logger LOG = Logger.getLogger(StatisticDiagramm.class.getName());
	private final DataSeries dataSeries;
	private final ChartProperties chartProperties;
	private final AxisProperties axisProperties;
	private final LegendProperties legendProperties;
	private final List<double[]> datas = new LinkedList<double[]>();
	private final List<Paint> paints = new LinkedList<Paint>();
	private transient Image image;

	/**
	 * @param title
	 * @param xLabel
	 * @param yLabel
	 * @param data
	 *            each element is a dataset which contains a value for each
	 *            round
	 * @throws ChartDataException
	 */
	StatisticDiagramm(final String title, final String xLabel, final String yLabel, final double[][] data) {
		this(title, xLabel, yLabel, data, createLinearXLabels(data[0].length, 1));
	}

	/**
	 * @param title
	 * @param xLabel
	 * @param yLabel
	 * @param data
	 *            each element is a dataset which contains a value for each
	 *            round
	 * @param xAxisLabels
	 *            the labels of the x axis
	 * @param chartType
	 * @param myChartProperties
	 * @throws ChartDataException
	 */
	StatisticDiagramm(final String title, final String xLabel, final String yLabel, final double[][] data,
	        final String[] xAxisLabels) {
		assert data.length == CombatantSide.values().length : "each team must have a data set!";
		setSize(600, 333);

		dataSeries = new DataSeries(xAxisLabels, xLabel, yLabel, title);

		chartProperties = new ChartProperties();
		axisProperties = new AxisProperties();
		axisProperties.getXAxisProperties().setShowGridLines(AxisTypeProperties.GRID_LINES_ONLY_WITH_LABELS);
		legendProperties = null;

		addData(data);

		addComponentListener(this);
	}

	/**
	 * adds the data as line diagram with default team colors
	 * 
	 * @param data
	 */
	protected void addData(final double[][] data) {
		addData(data, CombatantSide.ATTACKER.color(), CombatantSide.DEFENDER.color());
	}

	/**
	 * adds the data as given diagramm
	 * 
	 * @param data
	 * @param color1
	 * @param color2
	 * @param chartType
	 * @param myChartProperties
	 */
	protected void addData(final double[][] data, final Color color1, final Color color2) {
		for (int i = 0; i < data.length; i++) {
			datas.add(data[i]);
		}
		paints.add(color1);
		paints.add(color2);
		// don't block
		new AsyncImageUpdater().start();
	}

	/**
	 * updates the diagram image in a separate thread
	 * 
	 * @author daniel
	 */
	class AsyncImageUpdater extends Thread {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			updateImageData();
		}
	}

	/**
     * 
     */
	void updateImageData() {
		try {
			final double[][] axisData = datas.toArray(new double[datas.size()][]);
			final Paint[] axisPaints = paints.toArray(new Paint[paints.size()]);
			final AxisChartDataSet axisChartDataSet = new AxisChartDataSet(axisData, null, axisPaints, ChartType.LINE,
			        defaultLineChartProperties());
			dataSeries.addIAxisPlotDataSet(axisChartDataSet);
			image = generateImage();
		} catch (final ChartDataException e) {
			// seems to not be thrown
			LOG.severe("data not correct for chart! " + e.toString());
		}
	}

	/**
	 * @return
	 */
	protected ChartTypeProperties defaultLineChartProperties() {
		final Stroke[] strokes = new Stroke[datas.size()];
		for (int i = 0; i < strokes.length; i++) {
			strokes[i] = LineChartProperties.DEFAULT_LINE_STROKE;
		}
		final Shape[] shapes = new Shape[datas.size()];
		final LineChartProperties lineChartProperties = new LineChartProperties(strokes, shapes);
		return lineChartProperties;
	}

	/**
	 * returns a list of numbers from 1 to given number of labels
	 * 
	 * @param amount
	 *            the number of labes to produce
	 * @param modifier
	 *            this is the step in which the labels will be incremented
	 * @return
	 */
	protected static String[] createLinearXLabels(final int amount, final int modifier) {
		final String[] xAxisLabels = new String[amount];
		for (int i = 0; i < xAxisLabels.length; i++) {
			xAxisLabels[i] = Integer.toString((i + 1) * modifier);
		}
		return xAxisLabels;
	}

	/**
	 * creates the diagram for the given size
	 * 
	 * @param paintWidth
	 * @param paintHeight
	 * @return
	 */
	protected AxisChart generateChart(final int paintWidth, final int paintHeight) {
		return new AxisChart(dataSeries, chartProperties, axisProperties, legendProperties, paintWidth, paintHeight);

	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * @return an image to display if something went wrong
	 */
	protected Image errorImage() {
		final BufferedImage error = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		final Graphics g = error.getGraphics();
		g.setColor(Color.MAGENTA);
		g.fillRect(0, 0, error.getWidth(), error.getHeight());
		g.setColor(Color.BLACK);
		g.drawString("Error", 0, error.getHeight());
		return error;
	}

	/**
	 * create a image for the current chart
	 * 
	 * @return
	 */
	protected Image generateImage() {
		if (getHeight() == 0 || getWidth() == 0) {
			return null;
		}

		final AxisChart chart = generateChart(getWidth(), getHeight());
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		Image chartImage;
		try {
			org.jCharts.encoders.PNGEncoder.encode(chart, output);
			chartImage = new ImageIcon(output.toByteArray()).getImage();
		} catch (final ChartDataException e) {
			LOG.warning("could not create image for chart! " + e.toString());
			chartImage = errorImage();
		} catch (final PropertyException e) {
			LOG.warning("could not create image for chart! " + e.toString());
			chartImage = errorImage();
		} catch (final IOException e) {
			LOG.warning("could not create image for chart! " + e.toString());
			chartImage = errorImage();
		}
		return chartImage;
	}

	/**
	 * @return
	 */
	Icon createIcon() {
		return new ImageIcon(image);
	}

	/**
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(final ComponentEvent event) {
		// create image for new size
		image = generateImage();
	}

	/**
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	public void componentMoved(final ComponentEvent e) {
		// ignore
	}

	/**
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	public void componentShown(final ComponentEvent e) {
		// ignore
	}

	/**
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	public void componentHidden(final ComponentEvent e) {
		// ignore
	}

}
