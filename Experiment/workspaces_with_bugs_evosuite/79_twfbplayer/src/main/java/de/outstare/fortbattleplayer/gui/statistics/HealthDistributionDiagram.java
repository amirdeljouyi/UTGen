package de.outstare.fortbattleplayer.gui.statistics;

import java.util.Collection;
import java.util.Map;

import org.jCharts.axisChart.customRenderers.axisValue.renderers.ValueLabelPosition;
import org.jCharts.axisChart.customRenderers.axisValue.renderers.ValueLabelRenderer;
import org.jCharts.properties.ChartTypeProperties;
import org.jCharts.properties.ClusteredBarChartProperties;

/**
 * A HealthDistributionDiagram shows for a given range of health the number of
 * players.
 * 
 * @author daniel
 */
public class HealthDistributionDiagram extends StatisticDiagramm {
	private static final long serialVersionUID = -6432700595293197760L;

	/**
	 * @param title
	 * @param attackerLPDistData
	 * @param defenderLPDistData
	 */
	HealthDistributionDiagram(final String title, final Map<Number, ? extends Number> attackerLPDistData,
	        final Map<Number, ? extends Number> defenderLPDistData) {
		super(title, "health", "percent", createDiagramData(attackerLPDistData.values(), defenderLPDistData.values()),
		        createXAxisLabels(attackerLPDistData.keySet()));
	}

	/**
	 * @param labelData
	 * @return
	 */
	private static String[] createXAxisLabels(final Collection<? extends Object> labelData) {
		final String[] xAxisLabels = new String[labelData.size()];
		int i = 0;
		for (final Object label : labelData) {
			xAxisLabels[i] = label.toString();
			i++;
		}
		return xAxisLabels;
	}

	/**
	 * @param attackerData
	 * @param defenderData
	 * @return
	 */
	private static double[][] createDiagramData(final Collection<? extends Number> collection,
	        final Collection<? extends Number> collection2) {
		final double[][] result = new double[2][];
		result[0] = toDoubleArray(collection);
		result[1] = toDoubleArray(collection2);
		return result;
	}

	/**
	 * converts the given collection of numbers to an array of doubles
	 * 
	 * @param collection
	 *            may not be <code>null</code>
	 * @return will never be <code>null</code>
	 */
	private static double[] toDoubleArray(final Collection<? extends Number> collection) {
		assert collection != null : "pre condition violated: parameter may not be null!";
		final double[] doubles = new double[collection.size()];
		int i = 0;
		for (final Number n : collection) {
			doubles[i] = n.doubleValue();
			i++;
		}
		return doubles;
	}

	/**
	 * @return
	 */
	private static ChartTypeProperties barChartProperties() {
		final ClusteredBarChartProperties barChartProperties = new ClusteredBarChartProperties();
		final ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer(false, false, true, 0);
		valueLabelRenderer.setValueLabelPosition(ValueLabelPosition.AT_TOP);
		valueLabelRenderer.useVerticalLabels(false);
		barChartProperties.addPostRenderEventListener(valueLabelRenderer);
		return barChartProperties;
	}
}
