/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Mon Mar 18 03:27:31 GMT 2024
 */

package framework.util;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class StringUtils_Original_ESTest_scaffolding {

  @org.junit.jupiter.api.extension.RegisterExtension
  public org.evosuite.runtime.vnet.NonFunctionalRequirementExtension nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementExtension();

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeAll
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "framework.util.StringUtils"; 
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
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(StringUtils_Original_ESTest_scaffolding.class.getClassLoader() ,
      "org.apache.xerces.dom.ElementNSImpl",
      "org.apache.xml.utils.ObjectStack",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$TrailingMiscDispatcher",
      "org.apache.xml.dtm.ref.IncrementalSAXSource",
      "org.apache.xpath.functions.FuncNot",
      "org.apache.xerces.dom.AttrNSImpl",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$AttributeTraverser",
      "org.apache.xpath.compiler.XPathParser",
      "org.apache.xpath.XPath",
      "org.apache.xpath.objects.XNodeSet",
      "org.apache.xerces.impl.XMLNSDocumentScannerImpl$NSContentDispatcher",
      "org.apache.xerces.dom.DeferredNode",
      "org.apache.xerces.impl.dtd.XMLDTDDescription",
      "org.apache.xerces.dom.DeferredElementNSImpl",
      "org.apache.xpath.axes.BasicTestIterator",
      "org.apache.xml.utils.SAXSourceLocator",
      "org.apache.xerces.impl.dv.InvalidDatatypeValueException",
      "org.apache.xerces.dom.DeferredAttrImpl",
      "org.apache.xerces.impl.XMLScanner",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators",
      "org.apache.xerces.util.URI$MalformedURIException",
      "org.apache.xerces.impl.XMLEntityScanner",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$NamespaceTraverser",
      "org.apache.xerces.dom.ProcessingInstructionImpl",
      "org.apache.xerces.dom.CommentImpl",
      "org.apache.xerces.impl.dv.SecuritySupport",
      "framework.util.StringUtils",
      "org.apache.xerces.parsers.XMLParser",
      "org.apache.xpath.operations.UnaryOperation",
      "org.apache.log4j.Hierarchy",
      "org.apache.xerces.util.SymbolTable",
      "org.apache.xpath.functions.FuncNumber",
      "org.apache.xpath.functions.FunctionMultiArgs",
      "org.apache.xerces.impl.dv.ValidationContext",
      "org.apache.xerces.impl.dtd.XMLDTDValidator",
      "org.apache.xml.dtm.ref.dom2dtm.DOM2DTM",
      "org.apache.xml.dtm.ref.DTMAxisIteratorBase",
      "org.apache.xpath.functions.WrongNumberArgsException",
      "org.apache.xerces.impl.dv.ObjectFactory",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$ChildTraverser",
      "org.apache.xerces.dom.ChildNode",
      "org.apache.xpath.operations.Gte",
      "org.apache.xml.dtm.DTMDOMException",
      "org.apache.xerces.xni.grammars.XMLGrammarDescription",
      "org.apache.log4j.helpers.OptionConverter",
      "org.apache.xpath.functions.Function3Args",
      "org.apache.xerces.xni.parser.XMLErrorHandler",
      "org.apache.xerces.dom.EntityImpl",
      "org.apache.xerces.util.MessageFormatter",
      "org.apache.xml.dtm.ref.DTMDefaultBase",
      "org.apache.xml.dtm.ref.ExpandedNameTable$HashEntry",
      "org.apache.xerces.xni.XMLAttributes",
      "org.apache.xml.utils.XMLString",
      "org.apache.xerces.impl.io.MalformedByteSequenceException",
      "org.apache.xpath.functions.FuncSum",
      "org.apache.xerces.impl.Constants$ArrayEnumeration",
      "org.apache.xerces.dom.NotationImpl",
      "org.apache.xerces.dom.DeferredCommentImpl",
      "org.apache.xpath.XPathException",
      "org.apache.xerces.impl.dv.DTDDVFactory",
      "org.apache.xml.dtm.DTMAxisTraverser",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$DTDDispatcher",
      "org.apache.log4j.Category",
      "org.apache.xpath.Expression",
      "org.apache.xpath.operations.Gt",
      "org.apache.xml.dtm.SecuritySupport",
      "org.apache.xerces.dom.DeferredProcessingInstructionImpl",
      "org.apache.xerces.xni.parser.XMLDocumentSource",
      "org.apache.xerces.xni.XMLDTDContentModelHandler",
      "org.apache.xpath.functions.FuncBoolean",
      "org.apache.xerces.util.AugmentationsImpl$SmallContainer",
      "org.apache.xerces.impl.XMLErrorReporter",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$DescendantTraverser",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$FollowingSiblingTraverser",
      "org.apache.xerces.dom.DeferredEntityImpl",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$SelfTraverser",
      "org.apache.xerces.util.XMLStringBuffer",
      "org.apache.xerces.util.NamespaceSupport",
      "org.apache.xpath.axes.OneStepIterator",
      "org.apache.xerces.jaxp.JAXPConstants",
      "org.apache.xerces.xni.XMLString",
      "org.apache.xerces.impl.dv.DVFactoryException",
      "org.apache.xpath.functions.FuncPosition",
      "org.apache.xerces.impl.dv.DatatypeException",
      "org.apache.xerces.parsers.XML11Configurable",
      "org.apache.xerces.parsers.XIncludeAwareParserConfiguration",
      "org.apache.xerces.dom.DeferredAttrNSImpl",
      "org.apache.xpath.patterns.NodeTest",
      "org.apache.xerces.dom.NodeImpl",
      "org.apache.xerces.xni.parser.XMLDTDFilter",
      "org.apache.xerces.jaxp.DocumentBuilderImpl",
      "org.apache.xerces.dom.CoreDocumentImpl",
      "org.apache.log4j.spi.AppenderAttachable",
      "org.apache.xml.dtm.ObjectFactory",
      "org.apache.xpath.objects.LessThanOrEqualComparator",
      "org.apache.xpath.axes.WalkingIteratorSorted",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$DescendantFromRootTraverser",
      "org.apache.xerces.xni.Augmentations",
      "org.apache.xpath.operations.Div",
      "org.apache.xerces.impl.dv.dtd.ListDatatypeValidator",
      "org.apache.xpath.axes.PredicatedNodeTest",
      "org.apache.xerces.jaxp.JAXPValidatorComponent",
      "org.apache.xpath.objects.XMLStringFactoryImpl",
      "org.apache.xerces.xni.parser.XMLDTDContentModelFilter",
      "org.apache.xerces.impl.dv.SecuritySupport$1",
      "org.apache.xerces.impl.dv.SecuritySupport$2",
      "org.apache.xpath.functions.Function2Args",
      "org.apache.xerces.xs.XSObject",
      "org.apache.xerces.impl.dtd.XMLEntityDecl",
      "org.apache.xerces.impl.dv.SecuritySupport$3",
      "org.apache.xpath.objects.EqualComparator",
      "org.apache.xpath.functions.FuncConcat",
      "org.apache.xpath.functions.FuncDoclocation",
      "org.apache.xerces.parsers.ObjectFactory",
      "org.apache.xerces.xni.parser.XMLConfigurationException",
      "org.apache.xerces.dom.DeferredElementDefinitionImpl",
      "org.apache.xerces.impl.XML11NSDocumentScannerImpl",
      "org.apache.xerces.parsers.AbstractDOMParser",
      "org.apache.xpath.ExpressionOwner",
      "org.apache.xpath.patterns.UnionPattern",
      "org.apache.log4j.spi.RootCategory",
      "org.apache.xpath.functions.FuncStringLength",
      "org.apache.xerces.dom.ElementImpl",
      "org.apache.xerces.xni.parser.XMLInputSource",
      "org.apache.xerces.xni.parser.XMLComponentManager",
      "org.apache.xpath.domapi.XPathStylesheetDOM3Exception",
      "org.apache.xml.utils.NSInfo",
      "org.apache.xerces.dom.DeferredDocumentImpl",
      "org.apache.xerces.impl.io.UTF8Reader",
      "org.apache.xml.dtm.DTMManager",
      "org.apache.xerces.impl.dtd.XMLSimpleType",
      "org.apache.xerces.impl.XML11DocumentScannerImpl",
      "org.apache.xpath.operations.NotEquals",
      "org.apache.xerces.util.XMLChar",
      "org.apache.xpath.ExpressionNode",
      "org.apache.xerces.impl.XML11DTDScannerImpl",
      "org.apache.xpath.operations.Lte",
      "org.apache.log4j.helpers.Loader",
      "org.apache.xerces.xni.grammars.XMLGrammarLoader",
      "org.apache.xerces.xni.XMLDocumentHandler",
      "org.apache.xerces.dom.CDATASectionImpl",
      "org.apache.xml.dtm.DTMAxisIterator",
      "org.apache.xerces.impl.XMLEntityManager$CharacterBuffer",
      "org.apache.xml.dtm.Axis",
      "org.apache.xpath.SourceTreeManager",
      "org.apache.xpath.XPathVisitable",
      "org.apache.xerces.impl.validation.ValidationState",
      "org.apache.xpath.axes.DescendantIterator",
      "org.apache.xerces.dom.DeferredDocumentImpl$RefCount",
      "org.apache.xpath.objects.LessThanComparator",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$AncestorOrSelfTraverser",
      "org.apache.xerces.xni.NamespaceContext",
      "org.apache.xerces.xs.XSTypeDefinition",
      "org.apache.xerces.util.XMLSymbols",
      "org.apache.xalan.templates.FuncKey",
      "org.apache.xpath.axes.AxesWalker",
      "org.apache.xml.utils.PrefixResolver",
      "org.apache.xerces.impl.io.ASCIIReader",
      "org.apache.xpath.operations.Bool",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$PrologDispatcher",
      "org.apache.xerces.impl.XMLDocumentFragmentScannerImpl",
      "org.apache.xpath.functions.FuncLocalPart",
      "org.apache.xerces.parsers.SecuritySupport",
      "framework.util.TuningUtils",
      "org.apache.xpath.functions.FuncFloor",
      "org.apache.xerces.util.XMLAttributesImpl$Attribute",
      "org.apache.xerces.impl.dtd.DTDGrammar",
      "org.apache.xml.utils.XMLStringFactory",
      "org.apache.xpath.functions.FuncCurrent",
      "org.apache.xpath.functions.FuncString",
      "org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl",
      "org.apache.xpath.XPathAPI",
      "org.apache.xerces.xni.parser.XMLDocumentScanner",
      "org.apache.xerces.impl.XMLVersionDetector",
      "org.apache.xpath.objects.GreaterThanComparator",
      "org.apache.xpath.objects.GreaterThanOrEqualComparator",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators$TypedDescendantIterator",
      "org.apache.xpath.functions.FuncSubstring",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$DescendantOrSelfFromRootTraverser",
      "org.apache.xpath.objects.XBoolean",
      "org.apache.xpath.operations.Operation",
      "org.apache.xerces.impl.xs.XMLSchemaValidator",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$IndexedDTMAxisTraverser",
      "org.apache.xerces.xni.grammars.XMLDTDDescription",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$NamespaceDeclsTraverser",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$PrecedingSiblingTraverser",
      "org.apache.xerces.dom.DeferredEntityReferenceImpl",
      "org.apache.xml.dtm.ref.ExtendedType",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$XMLDeclDispatcher",
      "org.apache.xerces.xni.QName",
      "org.apache.xerces.util.XMLAttributesImpl",
      "org.apache.xpath.axes.OneStepIteratorForward",
      "org.apache.xerces.impl.XMLDocumentFragmentScannerImpl$ElementStack",
      "org.apache.xerces.impl.XMLEntityManager$InternalEntity",
      "org.apache.xpath.XPathProcessorException",
      "org.apache.log4j.PropertyConfigurator",
      "org.apache.xpath.axes.ChildIterator",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$PrecedingAndAncestorTraverser",
      "org.apache.xerces.impl.dtd.BalancedDTDGrammar",
      "org.apache.xerces.parsers.DOMParser",
      "org.apache.xerces.impl.dtd.XML11DTDProcessor",
      "org.apache.xml.dtm.SecuritySupport12",
      "org.apache.xpath.axes.FilterExprWalker",
      "org.apache.xpath.functions.FuncSubstringBefore",
      "org.apache.xpath.objects.XString",
      "org.apache.xerces.impl.XMLEntityManager$ExternalEntity",
      "org.apache.xerces.impl.dv.ObjectFactory$ConfigurationError",
      "org.apache.log4j.spi.LoggerRepository",
      "org.apache.xerces.dom.DeferredNotationImpl",
      "org.apache.xerces.util.SymbolTable$Entry",
      "org.apache.log4j.Level",
      "org.apache.xpath.operations.Number",
      "org.apache.xpath.functions.FuncExtFunctionAvailable",
      "org.apache.xerces.impl.XMLEntityManager$CharacterBufferPool",
      "org.apache.xerces.dom.DeferredTextImpl",
      "org.apache.xerces.xni.XMLResourceIdentifier",
      "org.apache.xpath.operations.Minus",
      "org.apache.xerces.impl.XMLEntityManager",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators$DescendantIterator",
      "org.apache.xerces.parsers.AbstractXMLDocumentParser",
      "org.apache.xerces.impl.dv.dtd.NOTATIONDatatypeValidator",
      "org.apache.xpath.objects.XObject",
      "org.apache.xerces.xni.parser.XMLParseException",
      "org.apache.xerces.impl.XMLNSDocumentScannerImpl",
      "org.apache.xpath.functions.FuncExtFunction",
      "org.apache.xerces.xni.parser.XMLDocumentFilter",
      "org.apache.xerces.xni.parser.XMLDTDSource",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$AllFromRootTraverser",
      "org.apache.xerces.impl.dtd.XMLAttributeDecl",
      "org.apache.xerces.dom.ElementDefinitionImpl",
      "org.apache.xpath.axes.SelfIteratorNoPredicate",
      "org.apache.xpath.compiler.Compiler",
      "org.apache.xml.dtm.ref.dom2dtm.DOM2DTMdefaultNamespaceDeclarationNode",
      "org.apache.xerces.dom.AttributeMap",
      "org.apache.xml.utils.DefaultErrorHandler",
      "org.apache.xerces.dom.EntityReferenceImpl",
      "org.apache.xpath.compiler.OpMapVector",
      "org.apache.xerces.dom.DeferredCDATASectionImpl",
      "org.apache.xerces.impl.io.Latin1Reader",
      "org.apache.xml.dtm.DTMConfigurationException",
      "org.apache.xerces.impl.dtd.XML11NSDTDValidator",
      "org.apache.xml.utils.WrappedRuntimeException",
      "org.apache.log4j.spi.DefaultRepositorySelector",
      "org.apache.xpath.operations.Mod",
      "org.apache.xpath.axes.UnionPathIterator",
      "org.apache.xerces.dom.DeferredDocumentTypeImpl",
      "org.apache.xpath.operations.Variable",
      "org.apache.xerces.util.AugmentationsImpl",
      "org.apache.xerces.impl.dv.DatatypeValidator",
      "org.apache.xpath.operations.Equals",
      "org.apache.xml.utils.XMLCharacterRecognizer",
      "org.apache.xml.dtm.ref.DTMNodeProxy",
      "org.apache.xpath.operations.Plus",
      "org.apache.xerces.impl.XMLDocumentFragmentScannerImpl$FragmentContentDispatcher",
      "org.apache.xerces.dom.CharacterDataImpl",
      "org.apache.xerces.impl.XMLEntityManager$1",
      "org.apache.log4j.or.ObjectRenderer",
      "org.apache.xpath.functions.FuncNormalizeSpace",
      "org.apache.xerces.xni.parser.XMLDTDScanner",
      "org.apache.xerces.impl.dv.dtd.StringDatatypeValidator",
      "org.apache.xpath.axes.WalkerFactory",
      "org.apache.xml.dtm.ref.DTMNodeIterator",
      "org.apache.xpath.compiler.Lexer",
      "org.apache.xpath.patterns.StepPattern",
      "org.apache.xerces.impl.dv.dtd.IDDatatypeValidator",
      "org.apache.xpath.VariableStack",
      "org.apache.xerces.impl.validation.ValidationManager",
      "org.apache.xml.dtm.DTMException",
      "org.apache.xpath.XPathContext$XPathExpressionContext",
      "org.apache.xpath.axes.PathComponent",
      "org.apache.xerces.xni.XNIException",
      "org.apache.xpath.functions.FuncCeiling",
      "org.apache.xml.utils.DOMHelper",
      "org.apache.xpath.functions.FuncFalse",
      "org.apache.xpath.functions.FunctionDef1Arg",
      "org.apache.xpath.functions.Function",
      "org.apache.xpath.axes.ChildTestIterator",
      "org.apache.log4j.spi.LoggerFactory",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$ContentDispatcher",
      "org.apache.log4j.spi.Configurator",
      "org.apache.xerces.impl.Constants",
      "org.apache.xml.dtm.ObjectFactory$ConfigurationError",
      "org.apache.xpath.objects.Comparator",
      "org.apache.regexp.RESyntaxException",
      "org.apache.xml.dtm.ref.sax2dtm.SAX2RTFDTM",
      "org.apache.xalan.extensions.ExpressionContext",
      "org.apache.xerces.xni.parser.XMLParserConfiguration",
      "org.apache.xpath.functions.FuncCount",
      "org.apache.xpath.functions.FuncLast",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$DescendantOrSelfTraverser",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$RootTraverser",
      "org.apache.xerces.impl.xs.identity.FieldActivator",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators$TypedChildrenIterator",
      "org.apache.xpath.functions.FuncSubstringAfter",
      "org.apache.xerces.dom.DocumentImpl",
      "org.apache.xpath.functions.FuncQname",
      "org.apache.xerces.xni.parser.XMLDTDContentModelSource",
      "org.apache.xpath.functions.FuncSystemProperty",
      "org.apache.xerces.xni.XMLLocator",
      "org.apache.xpath.compiler.FunctionTable",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators$TypedAncestorIterator",
      "org.apache.xerces.impl.XMLEntityHandler",
      "org.apache.log4j.Priority",
      "org.apache.xerces.xni.parser.XMLComponent",
      "org.apache.log4j.LogManager",
      "org.apache.xerces.impl.XMLEntityManager$ScannedEntity",
      "org.apache.xpath.objects.NotEqualComparator",
      "org.apache.xml.utils.ObjectVector",
      "org.apache.xpath.functions.FuncStartsWith",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators$TypedSingletonIterator",
      "org.apache.xerces.dom.AttrImpl",
      "org.apache.xerces.dom.NamedNodeMapImpl",
      "org.apache.xml.utils.IntStack",
      "org.apache.xerces.impl.XMLEntityManager$ByteBufferPool",
      "org.apache.log4j.DefaultCategoryFactory",
      "org.apache.log4j.or.RendererMap",
      "org.apache.xerces.xs.ItemPSVI",
      "org.apache.xerces.parsers.SecuritySupport$7",
      "org.apache.xpath.functions.FuncExtElementAvailable",
      "org.apache.xerces.impl.dv.dtd.ENTITYDatatypeValidator",
      "org.apache.xerces.xni.parser.XMLEntityResolver",
      "org.apache.xml.dtm.SecuritySupport12$1",
      "org.apache.xerces.parsers.SecuritySupport$3",
      "org.apache.xerces.impl.dtd.XMLNSDTDValidator",
      "org.apache.xerces.impl.XMLDTDScannerImpl",
      "org.apache.xerces.impl.dv.dtd.NMTOKENDatatypeValidator",
      "org.apache.xerces.parsers.SecuritySupport$4",
      "org.apache.xerces.parsers.SecuritySupport$6",
      "org.apache.xpath.axes.NodeSequence",
      "org.apache.xerces.parsers.SecuritySupport$1",
      "org.apache.xerces.parsers.SecuritySupport$2",
      "org.apache.xpath.axes.ReverseAxesWalker",
      "org.apache.xerces.impl.XMLEntityManager$RewindableInputStream",
      "org.apache.xml.utils.SuballocatedIntVector",
      "org.apache.xpath.objects.XNumber",
      "org.apache.xpath.axes.SubContextList",
      "org.apache.xml.dtm.DTMIterator",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$ParentTraverser",
      "org.apache.xpath.axes.LocPathIterator",
      "org.apache.xpath.compiler.OpMap",
      "org.apache.log4j.CategoryKey",
      "org.apache.xpath.operations.And",
      "org.apache.xerces.util.AugmentationsImpl$AugmentationsItemsContainer",
      "org.apache.xpath.axes.IteratorPool",
      "org.apache.xerces.impl.dtd.XMLElementDecl",
      "org.apache.xml.utils.NodeVector",
      "org.apache.regexp.RECompiler$RERange",
      "org.apache.xerces.xni.grammars.Grammar",
      "org.apache.xml.utils.DOM2Helper",
      "org.apache.xpath.axes.WalkingIterator",
      "org.apache.log4j.ProvisionNode",
      "org.apache.xerces.dom.CharacterDataImpl$1",
      "org.apache.regexp.RECompiler",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$AllFromNodeTraverser",
      "org.apache.xpath.XPathContext",
      "org.apache.xpath.patterns.FunctionPattern",
      "org.apache.xerces.impl.io.UCSReader",
      "org.apache.xerces.dom.DocumentTypeImpl",
      "org.apache.xml.dtm.ref.sax2dtm.SAX2DTM",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators$InternalAxisIteratorBase",
      "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl",
      "org.apache.xerces.impl.XMLEntityManager$Entity",
      "org.apache.xerces.util.XMLResourceIdentifierImpl",
      "org.apache.xml.dtm.ref.DTMManagerDefault",
      "org.apache.log4j.spi.RendererSupport",
      "org.apache.xpath.patterns.ContextMatchStepPattern",
      "org.apache.xerces.impl.dtd.XMLDTDLoader",
      "org.apache.xerces.parsers.ObjectFactory$ConfigurationError",
      "org.apache.xml.dtm.ref.ExpandedNameTable",
      "org.apache.xpath.axes.NodeSequence$IteratorCache",
      "org.apache.xerces.impl.dtd.XMLDTDProcessor",
      "org.apache.xpath.axes.AttributeIterator",
      "org.apache.regexp.RE",
      "org.apache.log4j.Logger",
      "org.apache.xerces.impl.XML11EntityScanner",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers",
      "org.apache.xpath.functions.FuncContains",
      "org.apache.xpath.operations.Or",
      "org.apache.xerces.impl.dv.dtd.IDREFDatatypeValidator",
      "org.apache.log4j.helpers.LogLog",
      "org.apache.xpath.functions.FuncRound",
      "org.apache.xerces.impl.dtd.DTDGrammarBucket",
      "org.apache.xerces.impl.msg.XMLMessageFormatter",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators$ChildrenIterator",
      "org.apache.xerces.xni.grammars.XMLGrammarPool",
      "org.apache.xerces.impl.XMLDocumentScannerImpl",
      "org.apache.xpath.functions.FuncLang",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$PrecedingTraverser",
      "org.apache.xerces.xni.parser.XMLPullParserConfiguration",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators$SingletonIterator",
      "org.apache.log4j.spi.RepositorySelector",
      "org.apache.xerces.xs.XSSimpleTypeDefinition",
      "org.apache.xerces.impl.XMLDocumentFragmentScannerImpl$Dispatcher",
      "org.apache.xpath.functions.FunctionOneArg",
      "org.apache.xml.utils.PrefixResolverDefault",
      "org.apache.log4j.or.DefaultRenderer",
      "org.apache.xml.utils.IntVector",
      "org.apache.xerces.jaxp.TeeXMLDocumentFilterImpl",
      "org.apache.regexp.CharacterIterator",
      "framework.ApplicationParameters",
      "org.apache.xerces.dom.DeferredElementImpl",
      "org.apache.xpath.operations.Mult",
      "org.apache.xerces.impl.RevalidationHandler",
      "org.apache.xpath.functions.FuncTrue",
      "org.apache.xpath.operations.Lt",
      "org.apache.xerces.util.AugmentationsImpl$LargeContainer",
      "org.apache.xpath.functions.FuncGenerateId",
      "org.apache.xpath.functions.FuncUnparsedEntityURI",
      "org.apache.commons.logging.impl.Log4JLogger",
      "org.apache.xerces.xni.XMLDTDHandler",
      "org.apache.xpath.operations.String",
      "org.apache.xerces.parsers.XML11Configuration",
      "org.apache.xpath.functions.FuncId",
      "org.apache.xerces.impl.dtd.XMLDTDValidatorFilter",
      "org.apache.regexp.StringCharacterIterator",
      "org.apache.xpath.functions.FuncNamespace",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$FollowingTraverser",
      "org.apache.regexp.REProgram",
      "org.apache.xerces.dom.ParentNode",
      "org.apache.xml.dtm.SecuritySupport12$6",
      "org.apache.xml.dtm.SecuritySupport12$7",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$AncestorTraverser",
      "org.apache.xml.dtm.SecuritySupport12$2",
      "org.apache.xml.dtm.SecuritySupport12$3",
      "org.apache.xerces.impl.validation.EntityState",
      "org.apache.xml.dtm.SecuritySupport12$4",
      "org.apache.xml.utils.TreeWalker",
      "org.apache.xerces.util.ParserConfigurationSettings",
      "org.apache.xpath.operations.Neg",
      "org.apache.xpath.functions.FuncTranslate",
      "org.apache.xerces.dom.TextImpl",
      "org.apache.xml.dtm.DTM",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators$AncestorIterator",
      "org.apache.xerces.impl.dtd.XML11DTDValidator"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(StringUtils_Original_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "org.apache.log4j.Level",
      "org.apache.log4j.Priority",
      "org.apache.commons.logging.impl.Log4JLogger",
      "org.apache.log4j.Category",
      "org.apache.log4j.Logger",
      "org.apache.log4j.Hierarchy",
      "org.apache.log4j.spi.RootCategory",
      "org.apache.log4j.or.DefaultRenderer",
      "org.apache.log4j.or.RendererMap",
      "org.apache.log4j.DefaultCategoryFactory",
      "org.apache.log4j.spi.DefaultRepositorySelector",
      "org.apache.log4j.helpers.OptionConverter",
      "org.apache.log4j.helpers.Loader",
      "org.apache.log4j.helpers.LogLog",
      "org.apache.log4j.PropertyConfigurator",
      "org.apache.log4j.LogManager",
      "org.apache.log4j.CategoryKey",
      "org.apache.log4j.ProvisionNode",
      "framework.util.StringUtils",
      "framework.util.TuningUtils",
      "org.apache.xpath.XPathAPI",
      "org.apache.xml.dtm.DTMManager",
      "org.apache.xpath.XPathContext",
      "org.apache.xml.utils.IntVector",
      "org.apache.xml.utils.IntStack",
      "org.apache.xml.utils.XMLStringFactory",
      "org.apache.xpath.objects.XMLStringFactoryImpl",
      "org.apache.xml.dtm.ObjectFactory",
      "org.apache.xml.dtm.SecuritySupport12",
      "org.apache.xml.dtm.SecuritySupport",
      "org.apache.xml.dtm.SecuritySupport12$4",
      "org.apache.xml.dtm.SecuritySupport12$7",
      "org.apache.xml.dtm.SecuritySupport12$1",
      "org.apache.xml.dtm.SecuritySupport12$2",
      "org.apache.xml.dtm.SecuritySupport12$3",
      "org.apache.xml.dtm.SecuritySupport12$6",
      "org.apache.xml.dtm.ref.DTMManagerDefault",
      "org.apache.xml.dtm.ref.ExtendedType",
      "org.apache.xml.dtm.ref.ExpandedNameTable",
      "org.apache.xml.dtm.ref.ExpandedNameTable$HashEntry",
      "org.apache.xml.utils.ObjectVector",
      "org.apache.xml.utils.ObjectStack",
      "org.apache.xpath.SourceTreeManager",
      "org.apache.xml.utils.NodeVector",
      "org.apache.xpath.XPathContext$XPathExpressionContext",
      "org.apache.xpath.VariableStack",
      "org.apache.xml.utils.PrefixResolverDefault",
      "org.apache.xpath.XPath",
      "org.apache.xpath.Expression",
      "org.apache.xpath.functions.Function",
      "org.apache.xpath.functions.FuncCurrent",
      "org.apache.xpath.functions.FuncLast",
      "org.apache.xpath.functions.FuncPosition",
      "org.apache.xpath.functions.FunctionOneArg",
      "org.apache.xpath.functions.FuncCount",
      "org.apache.xpath.functions.FuncId",
      "org.apache.xpath.functions.Function2Args",
      "org.apache.xalan.templates.FuncKey",
      "org.apache.xpath.functions.FunctionDef1Arg",
      "org.apache.xpath.functions.FuncLocalPart",
      "org.apache.xpath.functions.FuncNamespace",
      "org.apache.xpath.functions.FuncQname",
      "org.apache.xpath.functions.FuncGenerateId",
      "org.apache.xpath.functions.FuncNot",
      "org.apache.xpath.functions.FuncTrue",
      "org.apache.xpath.functions.FuncFalse",
      "org.apache.xpath.functions.FuncBoolean",
      "org.apache.xpath.functions.FuncLang",
      "org.apache.xpath.functions.FuncNumber",
      "org.apache.xpath.functions.FuncFloor",
      "org.apache.xpath.functions.FuncCeiling",
      "org.apache.xpath.functions.FuncRound",
      "org.apache.xpath.functions.FuncSum",
      "org.apache.xpath.functions.FuncString",
      "org.apache.xpath.functions.FuncStartsWith",
      "org.apache.xpath.functions.FuncContains",
      "org.apache.xpath.functions.FuncSubstringBefore",
      "org.apache.xpath.functions.FuncSubstringAfter",
      "org.apache.xpath.functions.FuncNormalizeSpace",
      "org.apache.xpath.functions.Function3Args",
      "org.apache.xpath.functions.FuncTranslate",
      "org.apache.xpath.functions.FunctionMultiArgs",
      "org.apache.xpath.functions.FuncConcat",
      "org.apache.xpath.functions.FuncSystemProperty",
      "org.apache.xpath.functions.FuncExtFunctionAvailable",
      "org.apache.xpath.functions.FuncExtElementAvailable",
      "org.apache.xpath.functions.FuncSubstring",
      "org.apache.xpath.functions.FuncStringLength",
      "org.apache.xpath.functions.FuncDoclocation",
      "org.apache.xpath.functions.FuncUnparsedEntityURI",
      "org.apache.xpath.compiler.FunctionTable",
      "org.apache.xml.utils.DefaultErrorHandler",
      "org.apache.xpath.compiler.XPathParser",
      "org.apache.xpath.compiler.OpMap",
      "org.apache.xpath.compiler.Compiler",
      "org.apache.xpath.compiler.Lexer",
      "org.apache.xpath.compiler.OpMapVector",
      "org.apache.xpath.axes.WalkerFactory",
      "org.apache.xpath.objects.XObject",
      "org.apache.xpath.objects.XNumber",
      "org.apache.xpath.patterns.NodeTest",
      "org.apache.xpath.axes.PredicatedNodeTest",
      "org.apache.xpath.axes.LocPathIterator",
      "org.apache.xpath.axes.WalkingIterator",
      "org.apache.xpath.axes.IteratorPool",
      "org.apache.xpath.axes.AxesWalker",
      "org.apache.xml.dtm.ref.DTMDefaultBase",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers",
      "org.apache.xml.dtm.ref.DTMDefaultBaseIterators",
      "org.apache.xml.dtm.ref.dom2dtm.DOM2DTM",
      "org.apache.xml.utils.SuballocatedIntVector",
      "org.apache.xml.utils.TreeWalker",
      "org.apache.xml.utils.NSInfo",
      "org.apache.xml.utils.DOMHelper",
      "org.apache.xml.utils.DOM2Helper",
      "org.apache.xml.dtm.ref.dom2dtm.DOM2DTMdefaultNamespaceDeclarationNode",
      "org.apache.xpath.axes.NodeSequence",
      "org.apache.xpath.objects.Comparator",
      "org.apache.xpath.objects.LessThanComparator",
      "org.apache.xpath.objects.LessThanOrEqualComparator",
      "org.apache.xpath.objects.GreaterThanComparator",
      "org.apache.xpath.objects.GreaterThanOrEqualComparator",
      "org.apache.xpath.objects.EqualComparator",
      "org.apache.xpath.objects.NotEqualComparator",
      "org.apache.xpath.objects.XNodeSet",
      "org.apache.xml.dtm.Axis",
      "org.apache.xml.dtm.DTMAxisTraverser",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$IndexedDTMAxisTraverser",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$DescendantTraverser",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$DescendantOrSelfTraverser",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$AllFromNodeTraverser",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$AllFromRootTraverser",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$RootTraverser",
      "org.apache.xml.dtm.ref.DTMNodeIterator",
      "org.apache.xml.dtm.ref.DTMDefaultBaseTraversers$ChildTraverser",
      "org.apache.xml.utils.XMLCharacterRecognizer",
      "framework.ApplicationParameters",
      "org.apache.regexp.RE",
      "org.apache.regexp.RECompiler",
      "org.apache.regexp.REProgram",
      "org.apache.regexp.StringCharacterIterator",
      "org.apache.regexp.RECompiler$RERange",
      "org.apache.regexp.RESyntaxException",
      "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl",
      "org.apache.xerces.jaxp.DocumentBuilderImpl",
      "org.apache.xerces.parsers.XMLParser",
      "org.apache.xerces.parsers.AbstractXMLDocumentParser",
      "org.apache.xerces.parsers.AbstractDOMParser",
      "org.apache.xerces.parsers.DOMParser",
      "org.apache.xerces.parsers.ObjectFactory",
      "org.apache.xerces.parsers.SecuritySupport",
      "org.apache.xerces.parsers.SecuritySupport$1",
      "org.apache.xerces.parsers.SecuritySupport$2",
      "org.apache.xerces.parsers.SecuritySupport$3",
      "org.apache.xerces.parsers.SecuritySupport$4",
      "org.apache.xerces.parsers.SecuritySupport$7",
      "org.apache.xerces.parsers.SecuritySupport$6",
      "org.apache.xerces.util.ParserConfigurationSettings",
      "org.apache.xerces.parsers.XML11Configuration",
      "org.apache.xerces.parsers.XIncludeAwareParserConfiguration",
      "org.apache.xerces.util.SymbolTable",
      "org.apache.xerces.impl.XMLEntityManager$1",
      "org.apache.xerces.impl.XMLEntityManager",
      "org.apache.xerces.util.XMLResourceIdentifierImpl",
      "org.apache.xerces.util.AugmentationsImpl",
      "org.apache.xerces.util.AugmentationsImpl$AugmentationsItemsContainer",
      "org.apache.xerces.util.AugmentationsImpl$SmallContainer",
      "org.apache.xerces.impl.XMLEntityManager$ByteBufferPool",
      "org.apache.xerces.impl.XMLEntityManager$CharacterBufferPool",
      "org.apache.xerces.impl.XMLEntityScanner",
      "org.apache.xerces.impl.XMLErrorReporter",
      "org.apache.xerces.impl.XMLScanner",
      "org.apache.xerces.impl.XMLDocumentFragmentScannerImpl",
      "org.apache.xerces.impl.XMLDocumentScannerImpl",
      "org.apache.xerces.impl.XMLNSDocumentScannerImpl",
      "org.apache.xerces.xni.XMLString",
      "org.apache.xerces.util.XMLStringBuffer",
      "org.apache.xerces.impl.XMLDocumentFragmentScannerImpl$ElementStack",
      "org.apache.xerces.xni.QName",
      "org.apache.xerces.impl.XMLDocumentFragmentScannerImpl$FragmentContentDispatcher",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$ContentDispatcher",
      "org.apache.xerces.impl.XMLNSDocumentScannerImpl$NSContentDispatcher",
      "org.apache.xerces.util.XMLAttributesImpl",
      "org.apache.xerces.util.XMLAttributesImpl$Attribute",
      "org.apache.xerces.impl.XMLEntityManager$Entity",
      "org.apache.xerces.impl.XMLEntityManager$ExternalEntity",
      "org.apache.xerces.util.NamespaceSupport",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$XMLDeclDispatcher",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$PrologDispatcher",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$DTDDispatcher",
      "org.apache.xerces.impl.XMLDocumentScannerImpl$TrailingMiscDispatcher",
      "org.apache.xerces.impl.dtd.XMLDTDDescription",
      "org.apache.xerces.impl.XMLDTDScannerImpl",
      "org.apache.xerces.impl.dtd.XMLDTDProcessor",
      "org.apache.xerces.impl.dtd.XMLEntityDecl",
      "org.apache.xerces.impl.dtd.XMLDTDValidator",
      "org.apache.xerces.impl.dtd.XMLNSDTDValidator",
      "org.apache.xerces.impl.validation.ValidationState",
      "org.apache.xerces.impl.dtd.XMLElementDecl",
      "org.apache.xerces.impl.dtd.XMLSimpleType",
      "org.apache.xerces.impl.dtd.XMLAttributeDecl",
      "org.apache.xerces.impl.dtd.DTDGrammarBucket",
      "org.apache.xerces.impl.dv.DTDDVFactory",
      "org.apache.xerces.impl.dv.ObjectFactory",
      "org.apache.xerces.impl.dv.SecuritySupport",
      "org.apache.xerces.impl.dv.SecuritySupport$1",
      "org.apache.xerces.impl.dv.SecuritySupport$2",
      "org.apache.xerces.impl.dv.SecuritySupport$3",
      "org.apache.xerces.impl.dv.dtd.StringDatatypeValidator",
      "org.apache.xerces.impl.dv.dtd.IDDatatypeValidator",
      "org.apache.xerces.impl.dv.dtd.IDREFDatatypeValidator",
      "org.apache.xerces.impl.dv.dtd.ListDatatypeValidator",
      "org.apache.xerces.impl.dv.dtd.ENTITYDatatypeValidator",
      "org.apache.xerces.impl.dv.dtd.NOTATIONDatatypeValidator",
      "org.apache.xerces.impl.dv.dtd.NMTOKENDatatypeValidator",
      "org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl",
      "org.apache.xerces.impl.validation.ValidationManager",
      "org.apache.xerces.impl.XMLVersionDetector",
      "org.apache.xerces.impl.msg.XMLMessageFormatter",
      "org.apache.xerces.xni.parser.XMLInputSource",
      "org.apache.xerces.impl.XMLEntityManager$RewindableInputStream",
      "org.apache.xerces.impl.io.UTF8Reader",
      "org.apache.xerces.impl.XMLEntityManager$ScannedEntity",
      "org.apache.xerces.impl.XMLEntityManager$CharacterBuffer",
      "org.apache.xerces.util.XMLSymbols",
      "org.apache.xerces.xni.NamespaceContext",
      "org.apache.xerces.dom.NodeImpl",
      "org.apache.xerces.dom.ChildNode",
      "org.apache.xerces.dom.ParentNode",
      "org.apache.xerces.dom.CoreDocumentImpl",
      "org.apache.xerces.dom.DocumentImpl",
      "org.apache.xerces.dom.DeferredDocumentImpl",
      "org.apache.xerces.dom.DeferredDocumentImpl$RefCount",
      "org.apache.xerces.util.XMLChar",
      "org.apache.xerces.util.SymbolTable$Entry",
      "org.apache.xerces.impl.Constants$ArrayEnumeration",
      "org.apache.xerces.impl.Constants",
      "org.apache.xerces.dom.ElementImpl",
      "org.apache.xerces.dom.DeferredElementImpl",
      "org.apache.xerces.dom.NamedNodeMapImpl",
      "org.apache.xerces.dom.AttributeMap",
      "org.apache.xerces.dom.CharacterDataImpl$1",
      "org.apache.xerces.dom.CharacterDataImpl",
      "org.apache.xerces.dom.TextImpl",
      "org.apache.xerces.dom.DeferredTextImpl",
      "org.apache.xerces.dom.CommentImpl",
      "org.apache.xerces.dom.DeferredCommentImpl"
    );
  }
}