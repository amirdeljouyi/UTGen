/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Tue Mar 19 20:17:35 GMT 2024
 */

package org.jfree.chart.plot;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class MultiplePiePlot_Original_ESTest_scaffolding {

  @org.junit.jupiter.api.extension.RegisterExtension
  public org.evosuite.runtime.vnet.NonFunctionalRequirementExtension nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementExtension();

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeAll
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "org.jfree.chart.plot.MultiplePiePlot"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.OFF; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init();
    setSystemProperties();
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @BeforeEach
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @AfterEach
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public static void setSystemProperties() {
 
    /*No java.lang.System property to set*/
  }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(MultiplePiePlot_Original_ESTest_scaffolding.class.getClassLoader() ,
      "org.jfree.util.PublicCloneable",
      "org.jfree.chart.event.ChartProgressListener",
      "org.jfree.chart.labels.StandardPieSectionLabelGenerator",
      "org.jfree.chart.labels.AbstractPieItemLabelGenerator",
      "org.jfree.util.Rotation",
      "org.jfree.chart.plot.PieLabelRecord",
      "org.jfree.chart.plot.ValueMarker",
      "org.jfree.text.TextFragment",
      "org.jfree.data.resources.DataPackageResources",
      "org.jfree.JCommon",
      "org.jfree.text.TextBox",
      "org.jfree.ui.about.Licences",
      "org.jfree.chart.block.ColumnArrangement",
      "org.jfree.base.modules.PackageManager$PackageConfiguration",
      "org.jfree.data.general.AbstractDataset",
      "org.jfree.chart.block.RectangleConstraint",
      "org.jfree.util.Log$SimpleMessage",
      "org.jfree.chart.title.Title",
      "org.jfree.chart.plot.Zoomable",
      "org.jfree.chart.labels.PieToolTipGenerator",
      "org.jfree.data.general.AbstractSeriesDataset",
      "org.jfree.chart.LegendItemCollection",
      "org.jfree.util.LogContext",
      "org.jfree.chart.axis.CategoryAxis",
      "org.jfree.data.RangeInfo",
      "org.jfree.data.DefaultKeyedValues2D",
      "org.jfree.ui.GradientPaintTransformType",
      "org.jfree.chart.renderer.RendererState",
      "org.jfree.base.modules.PackageManager",
      "org.jfree.ui.RectangleInsets",
      "org.jfree.chart.entity.StandardEntityCollection",
      "org.jfree.chart.plot.PiePlotState",
      "org.jfree.text.G2TextMeasurer",
      "org.jfree.chart.block.BorderArrangement",
      "org.jfree.util.SortOrder",
      "org.jfree.data.general.SeriesDataset",
      "org.jfree.chart.plot.CategoryPlot",
      "org.jfree.ui.TextAnchor",
      "org.jfree.chart.event.MarkerChangeListener",
      "org.jfree.data.xy.XYDataset",
      "org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset",
      "org.jfree.chart.block.Arrangement",
      "org.jfree.chart.plot.Plot",
      "org.jfree.chart.event.PlotChangeEvent",
      "org.jfree.ui.about.Contributor",
      "org.jfree.chart.title.LegendItemBlockContainer",
      "org.jfree.chart.plot.PiePlot",
      "org.jfree.chart.plot.ValueAxisPlot",
      "org.jfree.util.AttributedStringUtilities",
      "org.jfree.base.config.SystemPropertyConfiguration",
      "org.jfree.data.UnknownKeyException",
      "org.jfree.util.ObjectUtilities",
      "org.jfree.chart.event.AxisChangeEvent",
      "org.jfree.chart.LegendItem",
      "org.jfree.chart.plot.PlotRenderingInfo",
      "org.jfree.chart.block.CenterArrangement",
      "org.jfree.chart.block.Block",
      "org.jfree.chart.labels.PieSectionLabelGenerator",
      "org.jfree.base.AbstractBoot",
      "org.jfree.data.statistics.BoxAndWhiskerItem",
      "org.jfree.data.category.CategoryToPieDataset",
      "org.jfree.chart.axis.Axis",
      "org.jfree.chart.urls.PieURLGenerator",
      "org.jfree.base.config.HierarchicalConfiguration",
      "org.jfree.data.DefaultKeyedValues",
      "org.jfree.text.TextLine",
      "org.jfree.data.general.PieDataset",
      "org.jfree.base.Library",
      "org.jfree.base.modules.SubSystem",
      "org.jfree.data.statistics.BoxAndWhiskerCategoryDataset",
      "org.jfree.chart.block.BlockBorder",
      "org.jfree.ui.StandardGradientPaintTransformer",
      "org.jfree.data.general.DatasetUtilities",
      "org.jfree.base.BootableProjectInfo",
      "org.jfree.util.ExtendedConfiguration",
      "org.jfree.base.BasicProjectInfo$OptionalLibraryHolder",
      "org.jfree.chart.axis.AxisLocation",
      "org.jfree.util.AbstractObjectList",
      "org.jfree.data.xy.IntervalXYDataset",
      "org.jfree.data.general.DefaultPieDataset",
      "org.jfree.chart.event.ChartProgressEvent",
      "org.jfree.util.ObjectList",
      "org.jfree.util.LogTarget",
      "org.jfree.chart.plot.DefaultDrawingSupplier",
      "org.jfree.chart.block.LengthConstraintType",
      "org.jfree.data.category.DefaultIntervalCategoryDataset",
      "org.jfree.chart.plot.PieLabelDistributor",
      "org.jfree.JCommonInfo",
      "org.jfree.text.TextUtilities",
      "org.jfree.chart.axis.CategoryAnchor",
      "org.jfree.data.category.CategoryDataset",
      "org.jfree.chart.plot.DatasetRenderingOrder",
      "org.jfree.data.Range",
      "org.jfree.chart.event.RendererChangeListener",
      "org.jfree.data.KeyedValues",
      "org.jfree.base.BaseBoot",
      "org.jfree.chart.block.FlowArrangement",
      "org.jfree.util.Log",
      "org.jfree.chart.event.TitleChangeListener",
      "org.jfree.base.modules.Module",
      "org.jfree.data.category.IntervalCategoryDataset",
      "org.jfree.chart.plot.PlotState",
      "org.jfree.base.config.PropertyFileConfiguration",
      "org.jfree.chart.LegendItemSource",
      "org.jfree.chart.ChartColor",
      "org.jfree.base.modules.ModuleInfo",
      "org.jfree.base.modules.PackageState",
      "org.jfree.util.ShapeUtilities",
      "org.jfree.util.PaintUtilities",
      "org.jfree.text.TextBlock",
      "org.jfree.data.general.DatasetChangeListener",
      "org.jfree.chart.title.LegendGraphic",
      "org.jfree.util.Configuration",
      "org.jfree.chart.block.BlockParams",
      "org.jfree.text.TextMeasurer",
      "org.jfree.chart.event.TitleChangeEvent",
      "org.jfree.util.UnitType",
      "org.jfree.chart.axis.AxisSpace",
      "org.jfree.chart.PaintMap",
      "org.jfree.data.general.DatasetGroup",
      "org.jfree.data.DefaultKeyedValue",
      "org.jfree.ui.about.ProjectInfo",
      "org.jfree.ui.HorizontalAlignment",
      "org.jfree.chart.block.AbstractBlock",
      "org.jfree.chart.plot.XYPlot",
      "org.jfree.chart.event.ChartChangeEventType",
      "org.jfree.chart.block.BlockResult",
      "org.jfree.chart.entity.ChartEntity",
      "org.jfree.chart.entity.EntityCollection",
      "org.jfree.resources.JCommonResources",
      "org.jfree.data.Values2D",
      "org.jfree.ui.Size2D",
      "org.jfree.ui.RectangleAnchor",
      "org.jfree.data.general.SeriesChangeEvent",
      "org.jfree.ui.RectangleEdge",
      "org.jfree.chart.resources.JFreeChartResources",
      "org.jfree.data.general.DatasetChangeEvent",
      "org.jfree.data.general.SeriesChangeListener",
      "org.jfree.text.TextBlockAnchor",
      "org.jfree.ui.Drawable",
      "org.jfree.base.BasicProjectInfo",
      "org.jfree.ui.GradientPaintTransformer",
      "org.jfree.data.category.DefaultCategoryDataset",
      "org.jfree.chart.block.EntityBlockParams",
      "org.jfree.data.general.Dataset",
      "org.jfree.chart.block.EntityBlockResult",
      "org.jfree.chart.block.LabelBlock",
      "org.jfree.chart.plot.PlotOrientation",
      "org.jfree.chart.entity.PieSectionEntity",
      "org.jfree.chart.event.PlotChangeListener",
      "org.jfree.ui.VerticalAlignment",
      "org.jfree.chart.block.BlockContainer",
      "org.jfree.chart.JFreeChart",
      "org.jfree.chart.plot.Marker",
      "org.jfree.chart.plot.DrawingSupplier",
      "org.jfree.chart.title.TextTitle",
      "org.jfree.chart.title.LegendTitle",
      "org.jfree.util.TableOrder",
      "org.jfree.data.KeyedValue",
      "org.jfree.chart.ChartRenderingInfo",
      "org.jfree.chart.axis.ValueAxis",
      "org.jfree.data.Value",
      "org.jfree.chart.event.ChartChangeEvent",
      "org.jfree.chart.JFreeChartInfo",
      "org.jfree.chart.StrokeMap",
      "org.jfree.chart.plot.MultiplePiePlot",
      "org.jfree.chart.event.MarkerChangeEvent",
      "org.jfree.data.Values",
      "org.jfree.data.KeyedValues2D",
      "org.jfree.chart.event.AxisChangeListener",
      "org.jfree.data.KeyedObjects2D",
      "org.jfree.base.config.ModifiableConfiguration",
      "org.jfree.chart.event.ChartChangeListener",
      "org.jfree.chart.entity.LegendItemEntity"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(MultiplePiePlot_Original_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "org.jfree.util.UnitType",
      "org.jfree.ui.RectangleInsets",
      "org.jfree.chart.plot.Plot",
      "org.jfree.chart.plot.MultiplePiePlot",
      "org.jfree.util.TableOrder",
      "org.jfree.ui.RectangleEdge",
      "org.jfree.io.SerialUtilities",
      "org.jfree.data.general.AbstractDataset",
      "org.jfree.data.statistics.DefaultStatisticalCategoryDataset",
      "org.jfree.data.general.DatasetGroup",
      "org.jfree.data.KeyedObjects2D",
      "org.jfree.chart.ChartColor",
      "org.jfree.chart.plot.DefaultDrawingSupplier",
      "org.jfree.chart.plot.PiePlot",
      "org.jfree.util.Rotation",
      "org.jfree.chart.PaintMap",
      "org.jfree.chart.StrokeMap",
      "org.jfree.chart.labels.AbstractPieItemLabelGenerator",
      "org.jfree.chart.labels.StandardPieSectionLabelGenerator",
      "org.jfree.util.AbstractObjectList",
      "org.jfree.util.ObjectList",
      "org.jfree.base.Library",
      "org.jfree.base.BasicProjectInfo",
      "org.jfree.base.BootableProjectInfo",
      "org.jfree.ui.about.ProjectInfo",
      "org.jfree.chart.JFreeChartInfo",
      "org.jfree.chart.resources.JFreeChartResources",
      "org.jfree.ui.about.Licences",
      "org.jfree.ui.about.Contributor",
      "org.jfree.JCommonInfo",
      "org.jfree.resources.JCommonResources",
      "org.jfree.base.BasicProjectInfo$OptionalLibraryHolder",
      "org.jfree.base.AbstractBoot",
      "org.jfree.base.BaseBoot",
      "org.jfree.JCommon",
      "org.jfree.chart.JFreeChart",
      "org.jfree.chart.block.AbstractBlock",
      "org.jfree.ui.HorizontalAlignment",
      "org.jfree.ui.VerticalAlignment",
      "org.jfree.chart.title.Title",
      "org.jfree.chart.title.LegendTitle",
      "org.jfree.chart.block.FlowArrangement",
      "org.jfree.chart.block.ColumnArrangement",
      "org.jfree.chart.block.BlockBorder",
      "org.jfree.chart.block.BlockContainer",
      "org.jfree.ui.RectangleAnchor",
      "org.jfree.chart.event.ChartChangeEvent",
      "org.jfree.chart.event.TitleChangeEvent",
      "org.jfree.chart.event.ChartChangeEventType",
      "org.jfree.chart.title.TextTitle",
      "org.jfree.chart.renderer.AbstractRenderer",
      "org.jfree.data.general.AbstractSeriesDataset",
      "org.jfree.data.category.DefaultIntervalCategoryDataset",
      "org.jfree.data.general.DatasetChangeEvent",
      "org.jfree.chart.event.PlotChangeEvent",
      "org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset",
      "org.jfree.data.Range",
      "org.jfree.chart.axis.Axis",
      "org.jfree.chart.axis.CategoryAxis",
      "org.jfree.chart.event.AxisChangeEvent",
      "org.jfree.chart.axis.CategoryLabelPosition",
      "org.jfree.text.TextBlockAnchor",
      "org.jfree.ui.TextAnchor",
      "org.jfree.chart.axis.CategoryLabelWidthType",
      "org.jfree.chart.axis.CategoryLabelPositions",
      "org.jfree.chart.ChartPanel",
      "org.jfree.chart.PolarChartPanel",
      "org.jfree.chart.plot.PlotOrientation",
      "org.jfree.data.xy.AbstractXYDataset",
      "org.jfree.data.xy.AbstractIntervalXYDataset",
      "org.jfree.data.xy.XYIntervalSeriesCollection",
      "org.jfree.chart.annotations.AbstractXYAnnotation",
      "org.jfree.chart.annotations.XYTextAnnotation",
      "org.jfree.chart.annotations.XYPointerAnnotation",
      "org.jfree.chart.LegendItemCollection",
      "org.jfree.data.category.DefaultCategoryDataset",
      "org.jfree.data.jdbc.JDBCCategoryDataset",
      "org.jfree.data.DefaultKeyedValues2D",
      "org.jfree.data.time.RegularTimePeriod",
      "org.jfree.data.time.Day",
      "org.jfree.data.resources.DataPackageResources",
      "org.jfree.chart.axis.ValueAxis",
      "org.jfree.data.time.DateRange",
      "org.jfree.chart.axis.TickUnit",
      "org.jfree.chart.axis.DateTickUnit",
      "org.jfree.chart.axis.DateAxis$DefaultTimeline",
      "org.jfree.chart.axis.DateAxis",
      "org.jfree.chart.axis.TickUnits",
      "org.jfree.chart.axis.DateTickMarkPosition",
      "org.jfree.data.time.Hour",
      "org.jfree.date.SerialDate",
      "org.jfree.date.SpreadsheetDate",
      "org.jfree.data.time.Minute",
      "org.jfree.data.general.Series",
      "org.jfree.data.ComparableObjectSeries",
      "org.jfree.data.xy.YIntervalSeries",
      "org.jfree.data.ComparableObjectItem",
      "org.jfree.data.general.DefaultValueDataset",
      "org.jfree.chart.plot.MeterPlot",
      "org.jfree.chart.plot.DialShape",
      "org.jfree.chart.axis.NumberTickUnit",
      "org.jfree.chart.axis.NumberAxis",
      "org.jfree.chart.axis.CyclicNumberAxis",
      "org.jfree.data.RangeType",
      "org.jfree.data.DataUtilities",
      "org.jfree.chart.ChartRenderingInfo",
      "org.jfree.chart.entity.StandardEntityCollection",
      "org.jfree.chart.plot.PlotRenderingInfo",
      "org.jfree.chart.event.ChartProgressEvent",
      "org.jfree.chart.block.LengthConstraintType",
      "org.jfree.chart.block.RectangleConstraint",
      "org.jfree.chart.block.BlockParams",
      "org.jfree.ui.Size2D",
      "org.jfree.chart.block.BlockResult",
      "org.jfree.data.general.DatasetUtilities",
      "org.jfree.chart.plot.XYPlot",
      "org.jfree.chart.plot.CombinedDomainXYPlot",
      "org.jfree.chart.plot.DatasetRenderingOrder",
      "org.jfree.chart.plot.SeriesRenderingOrder",
      "org.jfree.chart.axis.AxisLocation",
      "org.jfree.data.gantt.TaskSeriesCollection",
      "org.jfree.chart.axis.CategoryAxis3D",
      "org.jfree.chart.title.DateTitle",
      "org.jfree.chart.axis.ExtendedCategoryAxis",
      "org.jfree.data.xy.AbstractXYZDataset",
      "org.jfree.data.xy.DefaultXYZDataset",
      "org.jfree.util.ObjectUtilities",
      "org.jfree.util.PaintUtilities",
      "org.jfree.util.ShapeUtilities",
      "org.jfree.chart.plot.CombinedRangeXYPlot",
      "org.jfree.data.time.Year",
      "org.jfree.data.time.TimePeriodFormatException",
      "org.jfree.chart.renderer.category.AbstractCategoryItemRenderer",
      "org.jfree.chart.renderer.category.BarRenderer",
      "org.jfree.chart.renderer.category.WaterfallBarRenderer",
      "org.jfree.util.BooleanList",
      "org.jfree.util.PaintList",
      "org.jfree.util.StrokeList",
      "org.jfree.util.ShapeList",
      "org.jfree.chart.labels.ItemLabelPosition",
      "org.jfree.chart.labels.ItemLabelAnchor",
      "org.jfree.chart.labels.StandardCategorySeriesLabelGenerator",
      "org.jfree.ui.StandardGradientPaintTransformer",
      "org.jfree.ui.GradientPaintTransformType",
      "org.jfree.chart.event.RendererChangeEvent",
      "org.jfree.data.time.Month",
      "org.jfree.chart.plot.CategoryPlot",
      "org.jfree.data.statistics.MeanAndStandardDeviation",
      "org.jfree.data.KeyedObjects",
      "org.jfree.data.KeyedObject",
      "org.jfree.chart.plot.ThermometerPlot",
      "org.jfree.ui.Layer",
      "org.jfree.data.general.DefaultKeyedValues2DDataset",
      "org.jfree.chart.annotations.TextAnnotation",
      "org.jfree.chart.annotations.CategoryTextAnnotation",
      "org.jfree.chart.annotations.CategoryPointerAnnotation",
      "org.jfree.chart.axis.CategoryAnchor",
      "org.jfree.text.G2TextMeasurer",
      "org.jfree.util.Log",
      "org.jfree.util.LogContext",
      "org.jfree.base.config.HierarchicalConfiguration",
      "org.jfree.base.config.PropertyFileConfiguration",
      "org.jfree.base.modules.PackageManager",
      "org.jfree.base.modules.PackageManager$PackageConfiguration",
      "org.jfree.base.config.SystemPropertyConfiguration",
      "org.jfree.text.TextUtilities",
      "org.jfree.text.TextBlock",
      "org.jfree.text.TextLine",
      "org.jfree.text.TextFragment",
      "org.jfree.util.Log$SimpleMessage",
      "org.jfree.data.time.Week",
      "org.jfree.chart.axis.PeriodAxis",
      "org.jfree.data.time.Quarter",
      "org.jfree.data.time.Second",
      "org.jfree.data.time.Millisecond",
      "org.jfree.chart.axis.PeriodAxisLabelInfo",
      "org.jfree.chart.axis.StandardTickUnitSource",
      "org.jfree.chart.plot.SpiderWebPlot",
      "org.jfree.chart.plot.CombinedRangeCategoryPlot",
      "org.jfree.util.SortOrder",
      "org.jfree.chart.plot.Marker",
      "org.jfree.chart.plot.ValueMarker",
      "org.jfree.ui.LengthAdjustmentType",
      "org.jfree.chart.entity.ChartEntity",
      "org.jfree.chart.axis.AxisSpace",
      "org.jfree.chart.axis.AxisState",
      "org.jfree.chart.axis.SegmentedTimeline",
      "org.jfree.chart.renderer.xy.AbstractXYItemRenderer",
      "org.jfree.chart.renderer.xy.XYDotRenderer",
      "org.jfree.chart.labels.StandardXYSeriesLabelGenerator",
      "org.jfree.chart.plot.CompassPlot",
      "org.jfree.chart.needle.MeterNeedle",
      "org.jfree.chart.needle.ArrowNeedle",
      "org.jfree.chart.title.CompositeTitle",
      "org.jfree.chart.block.BorderArrangement",
      "org.jfree.chart.axis.SymbolAxis",
      "org.jfree.chart.plot.FastScatterPlot",
      "org.jfree.data.xy.XYSeries",
      "org.jfree.chart.renderer.WaferMapRenderer",
      "org.jfree.chart.LegendItem",
      "org.jfree.chart.title.LegendGraphic",
      "org.jfree.chart.title.LegendItemBlockContainer",
      "org.jfree.chart.block.LabelBlock",
      "org.jfree.chart.block.CenterArrangement",
      "org.jfree.chart.entity.LegendItemEntity",
      "org.jfree.data.DefaultKeyedValues",
      "org.jfree.data.xy.XYDataItem",
      "org.jfree.data.general.WaferMapDataset",
      "org.jfree.data.general.SeriesChangeEvent",
      "org.jfree.chart.event.MarkerChangeEvent",
      "org.jfree.chart.plot.CategoryMarker",
      "org.jfree.chart.plot.CombinedDomainCategoryPlot",
      "org.jfree.data.time.FixedMillisecond",
      "org.jfree.chart.labels.AbstractCategoryItemLabelGenerator",
      "org.jfree.chart.labels.StandardCategoryItemLabelGenerator",
      "org.jfree.chart.renderer.RendererState",
      "org.jfree.chart.plot.PiePlotState",
      "org.jfree.chart.plot.PlotState",
      "org.jfree.chart.plot.WaferMapPlot",
      "org.jfree.chart.plot.RingPlot",
      "org.jfree.data.statistics.DefaultBoxAndWhiskerXYDataset",
      "org.jfree.data.xy.YIntervalSeriesCollection",
      "org.jfree.chart.axis.Tick",
      "org.jfree.chart.axis.ValueTick",
      "org.jfree.chart.axis.NumberTick",
      "org.jfree.chart.renderer.xy.XYStepAreaRenderer",
      "org.jfree.chart.plot.IntervalMarker",
      "org.jfree.data.xy.DefaultIntervalXYDataset",
      "org.jfree.chart.renderer.DefaultPolarItemRenderer",
      "org.jfree.data.xy.CategoryTableXYDataset",
      "org.jfree.data.xy.IntervalXYDelegate",
      "org.jfree.chart.axis.NumberAxis3D",
      "org.jfree.chart.plot.ColorPalette",
      "org.jfree.chart.plot.GreyPalette",
      "org.jfree.data.xy.DefaultOHLCDataset",
      "org.jfree.chart.renderer.xy.HighLowRenderer",
      "org.jfree.data.statistics.HistogramDataset",
      "org.jfree.data.statistics.HistogramType",
      "org.jfree.data.contour.DefaultContourDataset",
      "org.jfree.data.contour.NonGridContourDataset",
      "org.jfree.data.time.TimeSeries",
      "org.jfree.data.time.TimeSeriesCollection",
      "org.jfree.data.time.TimePeriodAnchor",
      "org.jfree.data.time.TimeTableXYDataset",
      "org.jfree.data.xy.DefaultWindDataset",
      "org.jfree.data.time.TimePeriodValuesCollection",
      "org.jfree.data.general.CombinedDataset",
      "org.jfree.data.general.SubSeriesDataset",
      "org.jfree.chart.axis.ModuloAxis",
      "org.jfree.chart.plot.MeterInterval",
      "org.jfree.chart.plot.PolarPlot",
      "org.jfree.data.statistics.SimpleHistogramDataset",
      "org.jfree.chart.renderer.category.LayeredBarRenderer",
      "org.jfree.chart.axis.SubCategoryAxis",
      "org.jfree.data.general.DefaultPieDataset",
      "org.jfree.data.general.DefaultKeyedValuesDataset",
      "org.jfree.chart.axis.ColorBar",
      "org.jfree.chart.plot.RainbowPalette",
      "org.jfree.chart.plot.ContourPlot",
      "org.jfree.data.jdbc.JDBCPieDataset",
      "org.jfree.chart.plot.PiePlot3D",
      "org.jfree.data.statistics.SimpleHistogramBin",
      "org.jfree.data.xy.DefaultTableXYDataset",
      "org.jfree.chart.labels.AbstractXYItemLabelGenerator",
      "org.jfree.chart.labels.StandardXYToolTipGenerator",
      "org.jfree.chart.renderer.xy.StandardXYItemRenderer",
      "org.jfree.data.xy.XIntervalSeries",
      "org.jfree.data.category.CategoryToPieDataset",
      "org.jfree.data.jdbc.JDBCXYDataset",
      "org.jfree.data.time.SimpleTimePeriod",
      "org.jfree.chart.axis.LogarithmicAxis",
      "org.jfree.chart.renderer.xy.CandlestickRenderer",
      "org.jfree.chart.renderer.category.IntervalBarRenderer",
      "org.jfree.chart.renderer.category.GanttRenderer",
      "org.jfree.chart.renderer.GrayPaintScale",
      "org.jfree.chart.title.PaintScaleLegend",
      "org.jfree.data.time.DynamicTimeSeriesCollection",
      "org.jfree.chart.renderer.category.LevelRenderer",
      "org.jfree.data.xy.XIntervalSeriesCollection",
      "org.jfree.chart.renderer.category.LineAndShapeRenderer",
      "org.jfree.chart.renderer.category.LineRenderer3D",
      "org.jfree.data.time.TimeSeriesDataItem",
      "org.jfree.chart.axis.SegmentedTimeline$Segment",
      "org.jfree.chart.labels.StandardXYZToolTipGenerator",
      "org.jfree.data.xy.XYBarDataset",
      "org.jfree.data.xy.MatrixSeries",
      "org.jfree.data.xy.DefaultXYDataset",
      "org.jfree.data.xy.OHLCDataItem",
      "org.jfree.data.xy.XYDatasetTableModel",
      "org.jfree.chart.title.ImageTitle",
      "org.jfree.chart.renderer.xy.XYBoxAndWhiskerRenderer",
      "org.jfree.chart.labels.BoxAndWhiskerXYToolTipGenerator",
      "org.jfree.data.DefaultKeyedValue",
      "org.jfree.data.time.TimePeriodValues",
      "org.jfree.chart.plot.PieLabelRecord",
      "org.jfree.chart.labels.IntervalCategoryItemLabelGenerator",
      "org.jfree.chart.labels.CustomXYToolTipGenerator",
      "org.jfree.chart.urls.TimeSeriesURLGenerator",
      "org.jfree.chart.renderer.xy.XYAreaRenderer",
      "org.jfree.chart.renderer.xy.StackedXYAreaRenderer",
      "org.jfree.chart.renderer.category.StackedBarRenderer",
      "org.jfree.chart.renderer.category.GroupedStackedBarRenderer",
      "org.jfree.data.KeyToGroupMap",
      "org.jfree.text.TextBox",
      "org.jfree.data.xy.DefaultHighLowDataset",
      "org.jfree.data.statistics.BoxAndWhiskerCalculator",
      "org.jfree.data.statistics.Statistics",
      "org.jfree.data.statistics.BoxAndWhiskerItem",
      "org.jfree.chart.needle.PlumNeedle",
      "org.jfree.chart.renderer.category.MinMaxCategoryRenderer",
      "org.jfree.chart.renderer.category.MinMaxCategoryRenderer$1",
      "org.jfree.chart.renderer.category.MinMaxCategoryRenderer$2",
      "org.jfree.chart.renderer.category.BarRenderer3D",
      "org.jfree.chart.renderer.xy.XYBlockRenderer",
      "org.jfree.chart.renderer.LookupPaintScale",
      "org.jfree.data.UnknownKeyException",
      "org.jfree.chart.renderer.xy.XYItemRendererState",
      "org.jfree.chart.renderer.xy.XYBarRenderer$XYBarRendererState",
      "org.jfree.data.xy.XYSeriesCollection",
      "org.jfree.chart.labels.StandardContourToolTipGenerator",
      "org.jfree.chart.renderer.category.StatisticalLineAndShapeRenderer",
      "org.jfree.chart.renderer.category.StatisticalBarRenderer",
      "org.jfree.chart.plot.PieLabelDistributor",
      "org.jfree.chart.renderer.category.AreaRenderer",
      "org.jfree.chart.renderer.category.StackedAreaRenderer",
      "org.jfree.chart.renderer.AreaRendererEndType",
      "org.jfree.data.gantt.TaskSeries",
      "org.jfree.ui.Align",
      "org.jfree.chart.urls.StandardXYURLGenerator",
      "org.jfree.chart.renderer.xy.XYBubbleRenderer",
      "org.jfree.data.xy.MatrixSeriesCollection",
      "org.jfree.chart.plot.CrosshairState",
      "org.jfree.chart.block.GridArrangement",
      "org.jfree.chart.axis.CyclicNumberAxis$CycleBoundTick",
      "org.jfree.chart.renderer.xy.CyclicXYItemRenderer",
      "org.jfree.chart.entity.PieSectionEntity",
      "org.jfree.chart.renderer.xy.XYBarRenderer",
      "org.jfree.chart.renderer.xy.ClusteredXYBarRenderer",
      "org.jfree.data.xy.XYIntervalSeries",
      "org.jfree.chart.labels.MultipleXYSeriesLabelGenerator",
      "org.jfree.chart.renderer.category.StackedBarRenderer3D",
      "org.jfree.chart.renderer.xy.XYLineAndShapeRenderer",
      "org.jfree.chart.renderer.xy.XYStepRenderer",
      "org.jfree.chart.renderer.category.BoxAndWhiskerRenderer",
      "org.jfree.chart.renderer.xy.XYErrorRenderer",
      "org.jfree.chart.entity.CategoryItemEntity",
      "org.jfree.chart.renderer.category.CategoryStepRenderer",
      "org.jfree.util.AttributedStringUtilities"
    );
  }
}