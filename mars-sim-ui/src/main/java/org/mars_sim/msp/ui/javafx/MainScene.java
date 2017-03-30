/**
 * Mars Simulation Project
 * MainScene.java
 * @version 3.1.0 2017-01-24
 * @author Lars Næsbye Christensen
 */

package org.mars_sim.msp.ui.javafx;


import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXRippler.RipplerMask;
import com.jfoenix.controls.JFXRippler.RipplerPos;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXSlider.IndicatorPosition;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;
//import com.jidesoft.swing.MarqueePane;
import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;


import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.StatusBar;
import org.controlsfx.control.action.Action;
//import org.controlsfx.glyphfont.FontAwesome;
//import org.eclipse.fx.ui.controls.tabpane.DndTabPane;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;
//import org.kordamp.ikonli.fontawesome.FontAwesome;

import com.sun.management.OperatingSystemMXBean;

import de.codecentric.centerdevice.MenuToolkit;
import de.jensd.fx.fontawesome.Icon;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.HPos;

import javafx.util.Duration;
import jiconfont.icons.FontAwesome;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import static javafx.geometry.Orientation.VERTICAL;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.swing.AbstractAction;
import javax.swing.DesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.mars_sim.msp.core.Msg;
import org.mars_sim.msp.core.RandomUtil;
import org.mars_sim.msp.core.Simulation;
import org.mars_sim.msp.core.SimulationConfig;
import org.mars_sim.msp.core.person.ai.mission.BuildingConstructionMission;
import org.mars_sim.msp.core.structure.Settlement;
import org.mars_sim.msp.core.structure.building.BuildingManager;
import org.mars_sim.msp.core.structure.construction.ConstructionManager;
import org.mars_sim.msp.core.structure.construction.ConstructionSite;
import org.mars_sim.msp.core.time.EarthClock;
import org.mars_sim.msp.core.time.MarsClock;
import org.mars_sim.msp.core.time.MasterClock;
import org.mars_sim.msp.core.time.UpTimer;
import org.mars_sim.msp.ui.javafx.BorderSlideBar;
import org.mars_sim.msp.ui.javafx.autofill.AutoFillTextBox;
import org.mars_sim.msp.ui.javafx.notification.MessagePopup;
import org.mars_sim.msp.ui.javafx.notification.PNotification;
import org.mars_sim.msp.ui.javafx.quotation.QuotationPopup;
//import org.mars_sim.msp.ui.steelseries.tools.Orientation;
import org.mars_sim.msp.ui.swing.DesktopPane;
import org.mars_sim.msp.ui.swing.ImageLoader;
import org.mars_sim.msp.ui.swing.MainDesktopPane;
import org.mars_sim.msp.ui.swing.UIConfig;
import org.mars_sim.msp.ui.swing.sound.AudioPlayer;
import org.mars_sim.msp.ui.swing.tool.StartUpLocation;
import org.mars_sim.msp.ui.swing.tool.construction.ConstructionWizard;
import org.mars_sim.msp.ui.swing.tool.guide.GuideWindow;
import org.mars_sim.msp.ui.swing.tool.mission.MissionWindow;
import org.mars_sim.msp.ui.swing.tool.monitor.MonitorWindow;
import org.mars_sim.msp.ui.swing.tool.navigator.NavigatorWindow;
import org.mars_sim.msp.ui.swing.tool.resupply.ResupplyWindow;
import org.mars_sim.msp.ui.swing.tool.resupply.TransportWizard;
import org.mars_sim.msp.ui.swing.tool.science.ScienceWindow;
import org.mars_sim.msp.ui.swing.tool.search.SearchWindow;
import org.mars_sim.msp.ui.swing.tool.settlement.SettlementMapPanel;
import org.mars_sim.msp.ui.swing.tool.settlement.SettlementTransparentPanel;
import org.mars_sim.msp.ui.swing.tool.settlement.SettlementWindow;
import org.mars_sim.msp.ui.swing.tool.time.MarsCalendarDisplay;
import org.mars_sim.msp.ui.swing.tool.time.TimeWindow;
import org.mars_sim.msp.ui.swing.toolWindow.ToolWindow;
import org.mars_sim.msp.ui.swing.unit_window.person.PlannerWindow;


import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.*;
import static org.fxmisc.wellbehaved.event.EventPattern.*;
import static org.fxmisc.wellbehaved.event.InputHandler.Result.*;
import static org.fxmisc.wellbehaved.event.InputMap.*;

//import org.reactfx.util.FxTimer;
//import org.reactfx.util.Timer;


/**
 * The MainScene class is the primary Stage for MSP. It is the container for
 * housing desktop swing node, javaFX UI, pull-down menu and icons for tools.
 */
public class MainScene {
	private static Logger logger = Logger.getLogger(MainScene.class.getName());

	public static String OS = Simulation.OS.toLowerCase();//System.getProperty("os.name").toLowerCase(); // e.g. 'linux', 'mac os x'

	private static final int TIME_DELAY = SettlementWindow.TIME_DELAY;

	private static final int SYSTEM_THEME = 0;
	private static final int NIMROD_THEME = 1;
	private static final int NIMBUS_THEME = 2;

	public static final int MAIN_TAB = 0;
	public static final int MAP_TAB = 1;
	public static final int HELP_TAB = 2;

	public static final int LOADING = 0;
	public static final int SAVING = 1;
	public static final int PAUSED = 2;

	public static final int DEFAULT_WIDTH = 1280;//1366;
	public static final int DEFAULT_HEIGHT = 768;

	private static final double ROTATION_CHANGE = Math.PI / 20D;

	private static final String ROUND_BUTTONS_DIR = "/icons/round_buttons/";

	private static final String PAUSE = "PAUSE";
	private static final String ESC_TO_RESUME = "ESC to resume";
	private static final String PAUSE_MSG = " [PAUSE]";// : ESC to resume]";
	private static final String LAST_SAVED = "Last Saved : ";
	private static final String EARTH_DATE_TIME = "EARTH  :  ";
	private static final String MARS_DATE_TIME = "MARS  :  ";
	private static final String UMST = " (UMST)";
	private static final String ONE_SPACE = " ";

	private static final String UPTIME = "UpTime :";
	private static final String TPS = "Ticks/s :";
	private static final String SEC = "1 real sec :";
	private static final String TR = "Time Ratio :";

	private static int theme = -1; // 6 is snow blue; 7 is the mud orange with nimrod
	public static int chatBoxHeight = 256;
	public static int LINUX_WIDTH = 270;
	public static int MACOS_WIDTH = 230;
	public static int WIN_WIDTH = 230;

	public static boolean menuBarVisible = false;

	private int solElapsedCache = 0;

	private double newTimeRatio = 0;
	private double initial_time_ratio = 0;

	private boolean isMuteCache;
	private boolean flag = true;
	private boolean isMainSceneDoneLoading = false;
	private boolean isFullScreenCache = false;

	private DoubleProperty sceneWidth;// = new SimpleDoubleProperty(DEFAULt_WIDTH);//1366-40;
	private DoubleProperty sceneHeight;// = new SimpleDoubleProperty(DEFAULt_HEIGHT); //768-40;

	private volatile transient ExecutorService mainSceneExecutor;

	private String themeSkin = "nimrod";
	private String title = null;
	private String dir = null;
	private String oldLastSaveStamp = null;

	private Pane root;
	private StackPane mainAnchorPane, //monPane,
					mapStackPane, minimapStackPane,
					speedPane, soundPane, calendarPane,
					settlementBox, chatBoxPane;

	private FlowPane flowPane;
	private AnchorPane rootAnchorPane, mapAnchorPane ;
	private SwingNode swingNode, mapNode, minimapNode, monNode, missionNode, resupplyNode, sciNode, guideNode ;
	private Stage stage, loadingCircleStage, savingCircleStage, pausingCircleStage;
	private Scene scene;

	private File fileLocn = null;
	private Thread newSimThread;

	private IconNode soundIcon, marsNetIcon, speedIcon;
	private Button earthTimeButton, marsTimeButton;//, northHemi, southHemi;
	private Label lastSaveLabel, monthLabel, yearLabel, TPSLabel, upTimeLabel;

	private JFXComboBox<Settlement> sBox;
	private JFXBadge badgeIcon;
	private JFXSnackbar snackbar;
	private JFXToggleButton cacheToggle, calendarButton, minimapToggle, mapToggle;
	private JFXSlider zoomSlider, timeSlider;

	private static JFXSlider soundSlider;
	private JFXButton soundBtn, marsNetBtn, rotateCWBtn, rotateCCWBtn, recenterBtn, speedBtn; // miniMapBtn, mapBtn,
	private JFXPopup soundPopup, marsNetBox, marsCalendarPopup, simSpeedPopup;// marsTimePopup;
	private JFXTabPane jfxTabPane;
	private VBox mapLabelBox;
	private ChatBox chatBox;
	//private DndTabPane dndTabPane;
	private ESCHandler esc = null;

	private Tab mainTab;

	private Timeline timeline;
	private NotificationPane notificationPane;

	private DecimalFormat twoDigitFormat = new DecimalFormat(Msg.getString("twoDigitFormat")); //$NON-NLS-1$
	private DecimalFormat formatter = new DecimalFormat(Msg.getString("TimeWindow.decimalFormat")); //$NON-NLS-1$

	private MainDesktopPane desktop;
	private MainSceneMenu menuBar;

	private MarsNode marsNode;
	private TransportWizard transportWizard;
	private ConstructionWizard constructionWizard;

	private QuotationPopup quote;
	private MessagePopup messagePopup;

	private BorderSlideBar topFlapBar;

    private Simulation sim = Simulation.instance();
    private MasterClock masterClock = sim.getMasterClock();
	private EarthClock earthClock;
	private MarsClock marsClock;

	private SettlementWindow settlementWindow;
	private NavigatorWindow navWin;
	private SettlementMapPanel mapPanel;

	private static AudioPlayer soundPlayer;
	private MarsCalendarDisplay calendarDisplay;
	private UpTimer uptimer;

	//private List<DesktopPane> desktops;
	//private ObservableList<Screen> screens;

	/**
	 * Constructor for MainScene
	 *
	 * @param stage
	 */
	public MainScene(Stage stage) {
		//logger.info("MainScene's constructor() is on " + Thread.currentThread().getName() + " Thread");
		this.stage = stage;
		this.isMainSceneDoneLoading = false;

		sceneWidth = new SimpleDoubleProperty(DEFAULT_WIDTH);
		sceneHeight = new SimpleDoubleProperty(DEFAULT_HEIGHT);

		//logger.info("OS is " + OS);
		stage.setResizable(true);
		stage.setMinWidth(sceneWidth.get());//1024);
		stage.setMinHeight(sceneHeight.get());//480);
		stage.setFullScreenExitHint("Use Ctrl+F (or Meta+C in macOS) to toggle full screen mode");
		stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));

		// Detect if a user hits the top-right close button
		stage.setOnCloseRequest(e -> {
			boolean result = alertOnExit();
			if (!result)
				e.consume();
		});

		// Detect if a user hits ESC
		esc = new ESCHandler();
		setEscapeEventHandler(true, stage);

	}

	public void createIndicator() {
		// 2016-10-01 Added mainSceneExecutor for executing wait stages
		startMainSceneExecutor();
		createProgressCircle(LOADING);
		createProgressCircle(SAVING);
		createProgressCircle(PAUSED);
	}

	// 2015-12-28 Added setEscapeEventHandler()
	public void setEscapeEventHandler(boolean value, Stage stage) {
		if (value) {
			stage.addEventHandler(KeyEvent.KEY_PRESSED, esc);
		}
		else {
			stage.removeEventHandler(KeyEvent.KEY_PRESSED, esc);
		}
	}

	class ESCHandler implements EventHandler<KeyEvent> {

		public void handle(KeyEvent t) {
			if (t.getCode() == KeyCode.ESCAPE) {
				boolean isOnPauseMode = masterClock.isPaused();
				if (isOnPauseMode) {
					unpauseSimulation();
				}
				else {
					pauseSimulation();
				}
			}
		}
	}

	/**
	 * Sets up the UI theme and the two timers as a thread pool task
	 */
	public class MainSceneTask implements Runnable {
		public void run() {
			logger.info("MainScene's MainSceneTask is in " + Thread.currentThread().getName() + " Thread");
			// Set look and feel of UI.
			UIConfig.INSTANCE.useUIDefault();
		}
	}

	/**
	 * Prepares the transport wizard, construction wizard, autosave timer and earth timer
	 */
	public void prepareOthers() {
		//logger.info("MainScene's prepareOthers() is on " + Thread.currentThread().getName() + " Thread");
		uptimer = masterClock.getUpTimer();
		startEarthTimer();
		transportWizard = new TransportWizard(this, desktop);
		constructionWizard = new ConstructionWizard(this, desktop);

	}


	/**
	 * Pauses sim and opens the transport wizard
	 * @param buildingManager
	 */
	public synchronized void openTransportWizard(BuildingManager buildingManager) {
		//logger.info("MainScene's openTransportWizard() is on " + Thread.currentThread().getName() + " Thread");
		// normally on pool-4-thread-3 Thread
		// Note: make sure pauseSimulation() doesn't interfere with resupply.deliverOthers();
		// 2015-12-16 Track the current pause state
		boolean previous = startPause();

		Platform.runLater(() -> {
			transportWizard.deliverBuildings(buildingManager);
		});

		endPause(previous);
	}


	public TransportWizard getTransportWizard() {
		return transportWizard;
	}

	/**
 	 * Pauses sim and opens the construction wizard
	 * @param constructionManager
	 */
	// 2015-12-16 Added openConstructionWizard()
	public void openConstructionWizard(BuildingConstructionMission mission) { // ConstructionManager constructionManager,
		//logger.info("MainScene's openConstructionWizard() is in " + Thread.currentThread().getName() + " Thread");
		// Note: make sure pauseSimulation() doesn't interfere with resupply.deliverOthers();
		// 2015-12-16 Track the current pause state
		boolean previous = startPause();

		Platform.runLater(() -> {
				constructionWizard.selectSite(mission);
			});

		endPause(previous);
	}

	// 2015-12-16 Added getConstructionWizard()
	public ConstructionWizard getConstructionWizard() {
		return constructionWizard;
	}

	/**
	 * Setup key events using wellbehavedfx
	 */
	// 2016-11-14 Setup key events using wellbehavedfx
	public void setupKeyEvents() {
		InputMap<KeyEvent> f1 = consume(keyPressed(F1), e -> {
			jfxTabPane.getSelectionModel().select(MainScene.HELP_TAB);
/*
 			if (desktop.isToolWindowOpen(GuideWindow.NAME))
				SwingUtilities.invokeLater(() -> desktop.closeToolWindow(GuideWindow.NAME));
			else {
				//getJFXTabPane().getSelectionModel().select(MainScene.MAIN_TAB);
				SwingUtilities.invokeLater(() -> desktop.openToolWindow(GuideWindow.NAME));
			}
*/
		});
	    Nodes.addInputMap(root, f1);

		InputMap<KeyEvent> f2 = consume(keyPressed(F2), e -> {
			if (desktop.isToolWindowOpen(SearchWindow.NAME))
				SwingUtilities.invokeLater(() -> desktop.closeToolWindow(SearchWindow.NAME));
			else {
				//getJFXTabPane().getSelectionModel().select(MainScene.MAIN_TAB);
				SwingUtilities.invokeLater(() -> desktop.openToolWindow(SearchWindow.NAME));
			}
		});
	    Nodes.addInputMap(root, f2);

		InputMap<KeyEvent> f3 = consume(keyPressed(F3), e -> {
			if (desktop.isToolWindowOpen(TimeWindow.NAME))
				SwingUtilities.invokeLater(() ->desktop.closeToolWindow(TimeWindow.NAME));
			else {
				//getJFXTabPane().getSelectionModel().select(MainScene.MAIN_TAB);
				SwingUtilities.invokeLater(() ->desktop.openToolWindow(TimeWindow.NAME));
			}
		});
	    Nodes.addInputMap(root, f3);

		InputMap<KeyEvent> f4 = consume(keyPressed(F4), e -> {
			if (desktop.isToolWindowOpen(MonitorWindow.NAME)) {
				SwingUtilities.invokeLater(() ->desktop.closeToolWindow(MonitorWindow.NAME));
				//rootAnchorPane.getChildren().remove(monPane);
			}
			else {
				//getJFXTabPane().getSelectionModel().select(MainScene.MAIN_TAB);
				//rootAnchorPane.getChildren().add(monPane);
		        //AnchorPane.setRightAnchor(monPane, 0.0);
		        //AnchorPane.setBottomAnchor(monPane, 0.0);
				SwingUtilities.invokeLater(() ->desktop.openToolWindow(MonitorWindow.NAME));
			}
		});
	    Nodes.addInputMap(root, f4);

		InputMap<KeyEvent> f5 = consume(keyPressed(F5), e -> {
			if (desktop.isToolWindowOpen(MissionWindow.NAME))
				SwingUtilities.invokeLater(() ->desktop.closeToolWindow(MissionWindow.NAME));
			else {
				//getJFXTabPane().getSelectionModel().select(MainScene.MAIN_TAB);
				SwingUtilities.invokeLater(() ->desktop.openToolWindow(MissionWindow.NAME));
			}
		});
	    Nodes.addInputMap(root, f5);

		InputMap<KeyEvent> f6 = consume(keyPressed(F6), e -> {
			if (desktop.isToolWindowOpen(ScienceWindow.NAME))
				SwingUtilities.invokeLater(() ->desktop.closeToolWindow(ScienceWindow.NAME));
			else {
				//getJFXTabPane().getSelectionModel().select(MainScene.MAIN_TAB);
				SwingUtilities.invokeLater(() ->desktop.openToolWindow(ScienceWindow.NAME));
			}
		});
	    Nodes.addInputMap(root, f6);

		InputMap<KeyEvent> f7 = consume(keyPressed(F7), e -> {
			if (desktop.isToolWindowOpen(ResupplyWindow.NAME))
				SwingUtilities.invokeLater(() ->desktop.closeToolWindow(ResupplyWindow.NAME));
			else {
				//getJFXTabPane().getSelectionModel().select(MainScene.MAIN_TAB);
				SwingUtilities.invokeLater(() ->desktop.openToolWindow(ResupplyWindow.NAME));
			}
		});
	    Nodes.addInputMap(root, f7);

		InputMap<KeyEvent> ctrlQ = consume(keyPressed(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN)), e -> {
        	popAQuote();
        	mainAnchorPane.requestFocus();
		});
	    Nodes.addInputMap(root, ctrlQ);

		InputMap<KeyEvent> ctrlN = consume(keyPressed(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN)), e -> {
        	boolean isNotificationOn = !desktop.getEventTableModel().isNoFiring();
       		if (isNotificationOn) {
        		menuBar.getNotificationItem().setSelected(false);
                desktop.getEventTableModel().setNoFiring(true);
        	}
        	else {
        		menuBar.getNotificationItem().setSelected(true);
                desktop.getEventTableModel().setNoFiring(false);
        	}
		});
	    Nodes.addInputMap(root, ctrlN);

		InputMap<KeyEvent> ctrlF = consume(keyPressed(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN)), e -> {
           	boolean isFullScreen = stage.isFullScreen();
        	if (!isFullScreen) {
        		menuBar.getShowFullScreenItem().setSelected(true);
        		if (!isFullScreenCache)
        			stage.setFullScreen(true);
        	}
        	else {
        		menuBar.getShowFullScreenItem().setSelected(false);
        		if (isFullScreenCache)
        			stage.setFullScreen(false);
        	}
        	isFullScreenCache = stage.isFullScreen();
		});
	    Nodes.addInputMap(root, ctrlF);

		InputMap<KeyEvent> ctrlUp = consume(keyPressed(new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN)), e -> {
			//soundPlayer.volumeUp();
			soundSlider.setValue(soundSlider.getValue() + .5);
			//soundSlider.setValue(convertVolume2Slider(soundPlayer.getVolume() +.05));
		});
	    Nodes.addInputMap(root, ctrlUp);

		InputMap<KeyEvent> ctrlDown = consume(keyPressed(new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN)), e -> {
			//soundPlayer.volumeDown();
			soundSlider.setValue(soundSlider.getValue() - .5);
			//soundSlider.setValue(convertVolume2Slider(soundPlayer.getVolume() -.05));
		});
	    Nodes.addInputMap(root, ctrlDown);

		InputMap<KeyEvent> ctrlM = consume(keyPressed(new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN)), e -> {
			boolean isMute = menuBar.getMuteItem().isSelected();
			if (isMute) {
        		menuBar.getMuteItem().setSelected(false);
        		soundPlayer.setMute(true);
        		soundSlider.setValue(0);
        	}
        	else {
        		menuBar.getMuteItem().setSelected(true);
        		soundPlayer.setMute(false);
        		soundSlider.setValue(convertVolume2Slider(soundPlayer.getVolume()));
        	}

		});
	    Nodes.addInputMap(root, ctrlM);

		//InputMap<KeyEvent> ctrlN = consume(keyPressed(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN)), e -> {
		//	newSimulation();
		//});
	    //Nodes.addInputMap(root, ctrlN);

		InputMap<KeyEvent> ctrlS = consume(keyPressed(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)), e -> {
			saveSimulation(Simulation.SAVE_DEFAULT);
		});
	    Nodes.addInputMap(root, ctrlS);

		InputMap<KeyEvent> ctrlA = consume(keyPressed(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN)), e -> {
			saveSimulation(Simulation.SAVE_AS);
		});
	    Nodes.addInputMap(root, ctrlA);

		InputMap<KeyEvent> ctrlX = consume(keyPressed(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN)), e -> {
			alertOnExit();
		});
	    Nodes.addInputMap(root, ctrlX);

		InputMap<KeyEvent> ctrlT = consume(keyPressed(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN)), e -> {
			if (OS.contains("linux")) {
				//if (theme == 6)
				setTheme(0);
				//else if (theme == 0)
				//	setTheme(6);
			}
			else {
				if (theme == 6)
					setTheme(7);
				else if (theme == 7)
					setTheme(6);
			}
		});
	    Nodes.addInputMap(root, ctrlT);

	}

	// Toggle the full screen mode off
	public void updateFullScreenMode() {
		menuBar.getShowFullScreenItem().setSelected(false);
	}

	/**
	 * initializes the scene
	 *
	 * @return Scene
	 */
	@SuppressWarnings("unchecked")
	public Scene initializeScene() {
		//logger.info("MainScene's initializeScene() is on " + Thread.currentThread().getName() + " Thread");

		//see dpi scaling at http://news.kynosarges.org/2015/06/29/javafx-dpi-scaling-fixed/
		//"I guess we�ll have to wait until Java 9 for more flexible DPI support.
		//In the meantime I managed to get JavaFX DPI scale factor,
		//but it is a hack (uses both AWT and JavaFX methods):"

		// Number of actual horizontal lines (768p)
		double trueHorizontalLines = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		// Number of scaled horizontal lines. (384p for 200%)
		double scaledHorizontalLines = Screen.getPrimary().getBounds().getHeight();
		// DPI scale factor.
		double dpiScaleFactor = trueHorizontalLines / scaledHorizontalLines;
		//logger.info("DPI Scale Factor is " + dpiScaleFactor);


		// Create group to hold swingNode which in turns holds the Swing desktop
		swingNode = new SwingNode();
		createSwingNode();

		mainAnchorPane = new StackPane();
		mainAnchorPane.getChildren().add(swingNode);
		mainAnchorPane.setMinHeight(sceneHeight.get());
		mainAnchorPane.setMinWidth(sceneWidth.get());
		// 2016-11-25 Setup root for embedding key events
		root = new Pane();//Group();

        soundPlayer = desktop.getSoundPlayer();

		// 2016-11-14 Setup key events using wellbehavedfx
		setupKeyEvents();

		IconFontFX.register(FontAwesome.getIconFont());

	    //2015-11-11 Added createFlyout()
		marsNetBox = createFlyout();
        flag = false;
        //EffectUtilities.makeDraggable(flyout.getScene().getRoot().getStage(), chatBox);
		// Create ControlFX's StatusBar
		//statusBar = createStatusBar();

        createLastSaveBar();
		createMarsTimeBar();
        createEarthTimeBar();

        createSpeedPanel();
        createSoundPopup();

        // Create menuBar
		menuBar = new MainSceneMenu(this, desktop);
		// Create Snackbar
		//createJFXSnackbar();
		// Create jfxTabPane
		createJFXTabs();

		rootAnchorPane = new AnchorPane();

		if (OS.contains("mac")) {
			((MenuBar)menuBar).useSystemMenuBarProperty().set(true);
		}

		//AnchorPane.setBottomAnchor(jfxTabPane, 0.0);
        AnchorPane.setLeftAnchor(jfxTabPane, 0.0);
        AnchorPane.setRightAnchor(jfxTabPane, 0.0);
        AnchorPane.setTopAnchor(jfxTabPane, 0.0);

        //AnchorPane.setRightAnchor(badgeIcon, 5.0);
        //AnchorPane.setTopAnchor(badgeIcon, 0.0);

		if (OS.contains("win")) {
	        AnchorPane.setTopAnchor(speedBtn, 3.0);
	        AnchorPane.setTopAnchor(marsNetBtn, 3.0);
	       // AnchorPane.setTopAnchor(mapBtn, 0.0);
	        //AnchorPane.setTopAnchor(miniMapBtn, 0.0);
	        AnchorPane.setTopAnchor(lastSaveLabel, 1.0);
	        AnchorPane.setTopAnchor(soundBtn, 3.0);
        	AnchorPane.setTopAnchor(earthTimeButton, 1.0);
        	AnchorPane.setTopAnchor(marsTimeButton, 1.0);
		}
		else if (OS.contains("linux")) {
	        AnchorPane.setTopAnchor(speedBtn, 0.0);
	        AnchorPane.setTopAnchor(marsNetBtn, 0.0);
	        //AnchorPane.setTopAnchor(mapBtn, -3.0);
	        //AnchorPane.setTopAnchor(miniMapBtn, -3.0);
	        AnchorPane.setTopAnchor(lastSaveLabel, 1.0);
	        AnchorPane.setTopAnchor(soundBtn, 0.0);
        	AnchorPane.setTopAnchor(earthTimeButton, 1.0);
        	AnchorPane.setTopAnchor(marsTimeButton, 1.0);
		}
		else if (OS.contains("mac")) {
	        AnchorPane.setTopAnchor(speedBtn, 0.0);
	        AnchorPane.setTopAnchor(marsNetBtn, 0.0);
	        //AnchorPane.setTopAnchor(mapBtn, -3.0);
	        //AnchorPane.setTopAnchor(miniMapBtn, -3.0);
	        AnchorPane.setTopAnchor(lastSaveLabel, 0.0);
	        AnchorPane.setTopAnchor(soundBtn, 0.0);
        	AnchorPane.setTopAnchor(earthTimeButton, 1.0);
        	AnchorPane.setTopAnchor(marsTimeButton, 1.0);
		}

        AnchorPane.setRightAnchor(speedBtn, 5.0);
        AnchorPane.setRightAnchor(marsNetBtn, 45.0);
        AnchorPane.setRightAnchor(soundBtn, 85.0);
        //AnchorPane.setRightAnchor(mapBtn, 125.0);
        //AnchorPane.setRightAnchor(miniMapBtn, 165.0);
        //AnchorPane.setLeftAnchor(earthTimeBar, sceneWidth.get()/2D);// - earthTimeBar.getPrefWidth());
        //AnchorPane.setLeftAnchor(marsTimeBar, sceneWidth.get()/2D - marsTimeBar.getPrefWidth());
        AnchorPane.setRightAnchor(marsTimeButton, 125.0);
        AnchorPane.setRightAnchor(earthTimeButton, marsTimeButton.getMinWidth() + 125);
        AnchorPane.setRightAnchor(lastSaveLabel,  marsTimeButton.getMinWidth() +  marsTimeButton.getMinWidth() + 125);
/*
        if (OS.contains("linux")) {
        	AnchorPane.setTopAnchor(earthTimeButton, 30.0);
        	AnchorPane.setTopAnchor(marsTimeButton, 30.0);
        }
        else {
        	AnchorPane.setTopAnchor(earthTimeButton, 35.0);
        	AnchorPane.setTopAnchor(marsTimeButton, 35.0);
        }
*/
        rootAnchorPane.getChildren().addAll(
        		jfxTabPane,
        		//monPane,
        		//miniMapBtn, mapBtn,
        		marsNetBtn, speedBtn,
        		lastSaveLabel,
        		earthTimeButton, marsTimeButton, soundBtn);//badgeIcon,borderPane, timeBar, snackbar

		root.getChildren().addAll(rootAnchorPane);

    	scene = new Scene(root, sceneWidth.get(), sceneHeight.get());//, Color.BROWN);

    	//scene.heightProperty().addListener((observable, oldValue, newValue) -> {
    	//    System.out.println("scene height : " + newValue);
    	//});
    	//scene.widthProperty().addListener((observable, oldValue, newValue) -> {
    	//    System.out.println("scene width : " + newValue);
    	//});


		jfxTabPane.prefHeightProperty().bind(scene.heightProperty());//.subtract(35));//73));
		jfxTabPane.prefWidthProperty().bind(scene.widthProperty());

		mainAnchorPane.prefHeightProperty().bind(scene.heightProperty().subtract(35));
		mainAnchorPane.prefWidthProperty().bind(scene.widthProperty());

		// anchorTabPane is within jfxTabPane
		mapAnchorPane.prefHeightProperty().bind(scene.heightProperty().subtract(35));//73));
		mapAnchorPane.prefWidthProperty().bind(scene.widthProperty());

		mapStackPane.prefHeightProperty().bind(scene.heightProperty().subtract(35));//73));

		//monPane.prefHeightProperty().bind(scene.heightProperty().divide(2));//.subtract(384));//73));
		//monPane.prefWidthProperty().bind(scene.widthProperty());

		//mapNodePane.heightProperty().addListener((observable, oldValue, newValue) -> {
    	//    System.out.println("mapNodePane height : " + newValue);
    	//});

		return scene;
	}

/*
	public void createJFXSnackbar() {
		snackbar = new JFXSnackbar();
		//snackbar.getStylesheets().clear();
		//snackbar.getStylesheets().add(getClass().getResource("/css/jfoenix-design.css").toExternalForm());
		//snackbar.getStylesheets().add(getClass().getResource("/css/jfoenix-components.css").toExternalForm());
		snackbar.setPrefSize(300, 40);
		snackbar.getStyleClass().add("jfx-snackbar");
		snackbar.registerSnackbarContainer(root);

		Icon icon = new Icon("INBOX");
		icon.setPadding(new Insets(10));
		badgeIcon = new JFXBadge(icon);
		badgeIcon.getStylesheets().clear();
		//badge1.getStylesheets().add(getClass().getResource("/css/jfoenix-design.css").toExternalForm());
		//badge1.getStylesheets().add(getClass().getResource("/css/jfoenix-components.css").toExternalForm());
		//badge1.getStyleClass().add("icons-badge");
		//badge1.setStyle("icons-badge");
		badgeIcon.setText("0");

		badgeIcon.setOnMouseClicked((e) -> {
			int value = Integer.parseInt(badgeIcon.getText());
			if (e.getButton() == MouseButton.PRIMARY) {
				value++;
			} else if (e.getButton() == MouseButton.SECONDARY) {
				if (value > 0)
					value--;
			}

			if (value == 0) {
				badgeIcon.setEnabled(false);
			} else {
				badgeIcon.setEnabled(true);
			}
			badgeIcon.setText(String.valueOf(value));

			// trigger snackbar
			if (count++%2==0){
				snackbar.fireEvent(new SnackbarEvent("Toast Message " + count));
			} else {
				snackbar.fireEvent(new SnackbarEvent("Snackbar Message "+ count,"UNDO",3000,(b)->{}));
			}
		});
	}
*/

	public void createEarthTimeBar() {
		earthTimeButton = new Button();
		earthTimeButton.setMaxWidth(Double.MAX_VALUE);

		if (OS.contains("linux")) {
			earthTimeButton.setMinWidth(LINUX_WIDTH);
			earthTimeButton.setPrefSize(LINUX_WIDTH, 29);
		}
		else if (OS.contains("mac")) {
			earthTimeButton.setMinWidth(MACOS_WIDTH);
			earthTimeButton.setPrefSize(MACOS_WIDTH, 28);
		}
		else {
			earthTimeButton.setMinWidth(WIN_WIDTH);
			earthTimeButton.setPrefSize(WIN_WIDTH, 33);
		}
/*
		earthTimeBar = new HBox();
		//earthTimeBar.setId("rich-blue");
		earthTimeBar.setMaxWidth(Double.MAX_VALUE);

		if (OS.contains("linux")) {
			earthTimeBar.setMinWidth(LINUX_WIDTH);
			earthTimeBar.setPrefSize(LINUX_WIDTH, 32);
		}
		else if (OS.contains("macos")) {
			earthTimeBar.setMinWidth(MACOS_WIDTH);
			earthTimeBar.setPrefSize(MACOS_WIDTH, 32);
		}
		else {
			earthTimeBar.setMinWidth(WIN_WIDTH);
			earthTimeBar.setPrefSize(WIN_WIDTH, 32);
		}
*/
		if (masterClock == null) {
			masterClock = sim.getMasterClock();
		}

		if (earthClock == null) {
			earthClock = masterClock.getEarthClock();
		}

		//earthTimeButton = new Button();
		//setQuickToolTip(earthTimeButton, "Click to see Quick Info on Mars");

/*		earthTimeButton.setOnAction(e -> {
			if (earthTimeFlag) {
				// TODO more here
				earthTimeFlag = false;
			}
			else {
				// TODO more here
				earthTimeFlag = true;
			}

            if (earthTimePopup.isVisible()) {
            	earthTimePopup.close();
            }
            else {
            	earthTimePopup.show(PopupVPosition.TOP, PopupHPosition.RIGHT, -5, 23);
            }

		});
*/
		earthTimeButton.setId("rich-blue");
		earthTimeButton.setMaxWidth(Double.MAX_VALUE);
		//earthTimeLabel.setMinWidth(180);
		//earthTimeLabel.setPrefSize(180, 30);
		//earthTimeButton.setTextAlignment(TextAlignment.LEFT);
		earthTimeButton.setAlignment(Pos.CENTER_LEFT);
		//earthTimeBar.getChildren().add(earthTimeButton);
	}


    /**
     * Creates and returns the panel for simulation speed and time info
     */
	// 2017-01-12 Added createSpeedPanel
	public void createSpeedPanel() {
		//logger.info("MainScene's createEarthTimeBox() is on " + Thread.currentThread().getName());

		speedBtn = new JFXButton();
		speedIcon = new IconNode(FontAwesome.CLOCK_O);
		speedIcon.setIconSize(20);
		//speedIcon.setFill(Color.YELLOW);
		//speedIcon.setStroke(Color.WHITE);

		speedBtn.setMaxSize(20, 20);
		speedBtn.setGraphic(speedIcon);
		setQuickToolTip(speedBtn, "Open Speed Panel");
		speedBtn.setOnAction(e -> {
            if (simSpeedPopup.isShowing()) {
            	simSpeedPopup.hide();//close();
            }
            else {
            	simSpeedPopup.show(speedBtn, PopupVPosition.TOP, PopupHPosition.RIGHT, -15, 35);
            }
		});

		speedPane = new StackPane();
		speedPane.getStyleClass().add("jfx-popup-container");
		speedPane.setAlignment(Pos.CENTER);
		speedPane.setPrefHeight(100);
		speedPane.setPrefWidth(earthTimeButton.getPrefWidth());

		simSpeedPopup = new JFXPopup(speedPane);

		//earthTimePopup.setOpacity(.5);
		//simSpeedPopup.setContent(speedPane);
		//simSpeedPopup.setPopupContainer(rootAnchorPane);
		//simSpeedPopup.setSource(speedBtn);

		// Set up a settlement view zoom bar
		timeSlider = new JFXSlider();
		timeSlider.getStyleClass().add("jfx-slider");
		timeSlider.setPrefHeight(180);
		timeSlider.setPrefHeight(20);
		timeSlider.setPadding(new Insets(0, 5, 0, 5));

		initial_time_ratio = Simulation.instance().getMasterClock().getDefaultTimeRatio();

		//timeSlider.prefHeightProperty().bind(mapNodePane.heightProperty().multiply(.3d));
		timeSlider.setMin(0); // need to be zero
		timeSlider.setMax(12);//initial_time_ratio*32D);//8D);
		timeSlider.setValue(7);//initial_time_ratio);
		timeSlider.setMajorTickUnit(1);//initial_time_ratio*4);
		timeSlider.setMinorTickCount(1);
		//timeSlider.setShowTickLabels(true);
		timeSlider.setShowTickMarks(true);
		timeSlider.setSnapToTicks(true);
		timeSlider.setBlockIncrement(1);//initial_time_ratio/32D);//4D);
		timeSlider.setOrientation(Orientation.HORIZONTAL);
		timeSlider.setIndicatorPosition(IndicatorPosition.RIGHT);

		setQuickToolTip(timeSlider, "Adjust Time Ratio"); //$NON-NLS-1$

        Label header_label = new Label("SPEED PANEL");
        //header_label.setAlignment(Pos.CENTER);
        header_label.setTextAlignment(TextAlignment.CENTER);
        header_label.setStyle("-fx-text-fill: black;"
        			+ "-fx-font-size: 12px;"
        		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
        			+ "-fx-font-weight: normal;");
        header_label.setPadding(new Insets(10, 5, 5, 10));

		String DEFAULT = " (Default : ";
		String CLOSE_PAR = ")";
        int default_ratio = (int)masterClock.getDefaultTimeRatio();
        StringBuilder s0 = new StringBuilder();

        Label time_ratio_label0 = new Label(TR);
        time_ratio_label0.setAlignment(Pos.CENTER_RIGHT);
        time_ratio_label0.setStyle("-fx-text-fill: #206982;"
        			+ "-fx-font-size: 12px;"
        		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
        			+ "-fx-font-weight: normal;");
		time_ratio_label0.setPadding(new Insets(1, 1, 1, 5));

        Label time_ratio_label = new Label();
        time_ratio_label.setStyle("-fx-text-fill: #206982;"
        			+ "-fx-font-size: 12px;"
        		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
        			+ "-fx-font-weight: normal;");
		time_ratio_label.setPadding(new Insets(1, 1, 1, 10));
		s0.append((int)initial_time_ratio).append(DEFAULT).append(default_ratio).append(CLOSE_PAR);
		time_ratio_label.setText(s0.toString());


        Label real_time_label0 = new Label(SEC);
        real_time_label0.setAlignment(Pos.CENTER_RIGHT);
        real_time_label0.setStyle("-fx-text-fill: #065185;"
        			+ "-fx-font-size: 12px;"
        		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
        			+ "-fx-font-weight: italic;");
        real_time_label0.setPadding(new Insets(1, 1, 1, 5));


        Label real_time_label = new Label();
        real_time_label.setStyle("-fx-text-fill: #065185;"
        			+ "-fx-font-size: 12px;"
        		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
        			+ "-fx-font-weight: italic;");
        real_time_label.setPadding(new Insets(1, 1, 1, 10));

		StringBuilder s1 = new StringBuilder();
		double ratio = masterClock.getTimeRatio();
		//String factor = String.format(Msg.getString("TimeWindow.timeFormat"), ratio); //$NON-NLS-1$
		s1.append(masterClock.getTimeTruncated(ratio));
		real_time_label.setText(s1.toString());

		// detect dragging
        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {

            	if (old_val != new_val) {

	            	double sliderValue = new_val.doubleValue();

	            	if (default_ratio <= 64)
	            		newTimeRatio = Math.pow(2, (int)sliderValue - 1);
	            	else if (default_ratio <= 128)
	            		newTimeRatio = Math.pow(2, (int)sliderValue);
	            	else if (default_ratio <= 256)
	            		newTimeRatio = Math.pow(2, (int)sliderValue + 1);
	            	else if (default_ratio <= 512)
	            		newTimeRatio = Math.pow(2, (int)sliderValue + 2);

	            	//System.out.println("sliderValue : " + sliderValue + "  newTimeRatio : " + newTimeRatio);

					masterClock.setTimeRatio(newTimeRatio);

	            	StringBuilder s0 = new StringBuilder();
					s0.append((int)newTimeRatio).append(DEFAULT).append(default_ratio).append(CLOSE_PAR);
					time_ratio_label.setText(s0.toString());

					StringBuilder s1 = new StringBuilder();
					s1.append(masterClock.getTimeTruncated(newTimeRatio));
					real_time_label.setText(s1.toString());

            	}
            }
        });


        Label TPSLabel0 = new Label(TPS);
        TPSLabel0.setAlignment(Pos.CENTER_RIGHT);
        TPSLabel0.setStyle("-fx-text-fill: #065185;"
    			+ "-fx-font-size: 12px;"
    		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
    			+ "-fx-font-weight: italic;");
        TPSLabel0.setPadding(new Insets(1, 1, 1, 5));

        TPSLabel = new Label();
        TPSLabel.setStyle("-fx-text-fill: #065185;"
    			+ "-fx-font-size: 12px;"
    		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
    			+ "-fx-font-weight: italic;");
        TPSLabel.setPadding(new Insets(1, 1, 1, 10));
		TPSLabel.setText(formatter.format(masterClock.getPulsesPerSecond()));

        Label upTimeLabel0 = new Label(UPTIME);
        upTimeLabel0.setAlignment(Pos.CENTER_RIGHT);
        upTimeLabel0.setTextAlignment(TextAlignment.RIGHT);
        upTimeLabel0.setStyle("-fx-text-fill: #065185;"
    			+ "-fx-font-size: 12px;"
    		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
    			+ "-fx-font-weight: italic;");
        upTimeLabel0.setPadding(new Insets(1, 1, 1, 5));

        upTimeLabel = new Label();
        upTimeLabel.setStyle("-fx-text-fill: #065185;"
    			+ "-fx-font-size: 12px;"
    		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
    			+ "-fx-font-weight: italic;");
        upTimeLabel.setPadding(new Insets(1, 1, 1, 10));
        if (uptimer != null)
        	upTimeLabel.setText (uptimer.getUptime());

        GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(1, 5, 1, 1));
		gridPane.setHgap(1.0);
		gridPane.setVgap(1.0);

		ColumnConstraints right = new ColumnConstraints();
	    right.setPrefWidth(earthTimeButton.getPrefWidth()*.6);
	    ColumnConstraints left = new ColumnConstraints();
	    left.setPrefWidth(earthTimeButton.getPrefWidth()*.4);

		GridPane.setConstraints(time_ratio_label, 1, 0);
		GridPane.setConstraints(real_time_label, 1, 1);
		GridPane.setConstraints(TPSLabel, 1, 2);
		GridPane.setConstraints(upTimeLabel, 1, 3);

		GridPane.setConstraints(time_ratio_label0, 0, 0);
		GridPane.setConstraints(real_time_label0, 0, 1);
		GridPane.setConstraints(TPSLabel0, 0, 2);
		GridPane.setConstraints(upTimeLabel0, 0, 3);

		GridPane.setHalignment(time_ratio_label, HPos.CENTER);
		GridPane.setHalignment(real_time_label, HPos.CENTER);
		GridPane.setHalignment(TPSLabel, HPos.CENTER);
		GridPane.setHalignment(upTimeLabel, HPos.CENTER);

		GridPane.setHalignment(time_ratio_label0, HPos.RIGHT);
		GridPane.setHalignment(real_time_label0, HPos.RIGHT);
		GridPane.setHalignment(TPSLabel0, HPos.RIGHT);
		GridPane.setHalignment(upTimeLabel0, HPos.RIGHT);

		gridPane.getColumnConstraints().addAll(left, right);
		gridPane.getChildren().addAll(time_ratio_label0, time_ratio_label, real_time_label0, real_time_label, TPSLabel0, TPSLabel, upTimeLabel0, upTimeLabel);

        VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(header_label, timeSlider, gridPane);
        speedPane.getChildren().addAll(vBox);

	}
/*
	public String timeRatioString(int t) {
		String s = null;
		if (t < 10)
			s = "   " + t;
		else if (t < 100)
			s = "  " + t;
		else if (t < 1000)
			s = " " + t;
		else
			s = "" + t;
		return s;
	}
*/
    public static Label createIconLabel(String iconName, int iconSize){
        return LabelBuilder.create()
                .text(iconName)
                .styleClass("icons")
                .style("-fx-font-size: " + iconSize + "px;")
                .build();
    }

    /**
     * Creates and returns the sound popup box
     */
	// 2017-01-25 Added createSoundPopup()
	public void createSoundPopup() {
		//logger.info("MainScene's createSoundPopup() is on " + Thread.currentThread().getName());

		soundBtn = new JFXButton();
		soundBtn.getStyleClass().add("button-raised");
		//Icon icon = new Icon("MUSIC");
		//icon.setCursor(Cursor.HAND);
		//icon.setStyle("-fx-background-color: orange;");
		//value.setPadding(new Insets(1));
		//Label bell = createIconLabel("\uf0a2", 15);
		//IconFontFX.register(FontAwesome.getIconFont());
		soundIcon = new IconNode(FontAwesome.BELL_O);
		soundIcon.setIconSize(20);
		//soundIcon.setFill(Color.YELLOW);
		//soundIcon.setStroke(Color.WHITE);

		soundBtn.setMaxSize(20, 20);
		soundBtn.setGraphic(soundIcon);
		setQuickToolTip(soundBtn, "Open Sound Panel");

		soundBtn.setOnAction(e -> {
            if (soundPopup.isShowing()) {
            	soundPopup.hide();//close();
            }
            else {
            	soundPopup.show(soundBtn, PopupVPosition.TOP, PopupHPosition.RIGHT, -15, 35);
            }

		});

		soundPane = new StackPane();
		soundPane.getStyleClass().add("jfx-popup-container");
		soundPane.setAlignment(Pos.CENTER);
		soundPane.setPrefHeight(75);
		soundPane.setPrefWidth(250);

		soundPopup = new JFXPopup(soundPane);
		//soundPopup.setContent(soundPane);
		//soundPopup.setPopupContainer(rootAnchorPane);
		//soundPopup.setSource(soundBtn);

		// Set up a settlement view zoom bar
		soundSlider = new JFXSlider();
		soundSlider.getStyleClass().add("jfx-slider");
		soundSlider.setPrefHeight(220);
		soundSlider.setPrefHeight(20);
		soundSlider.setPadding(new Insets(0, 15, 0, 15));

		soundSlider.setMin(0);
		soundSlider.setMax(10);
		soundSlider.setValue(convertVolume2Slider(soundPlayer.getVolume()));
		soundSlider.setMajorTickUnit(1);
		//soundSlider.setMinorTickCount();
		soundSlider.setShowTickLabels(true);
		soundSlider.setShowTickMarks(true);
		soundSlider.setSnapToTicks(true);
		soundSlider.setBlockIncrement(.5);
		soundSlider.setOrientation(Orientation.HORIZONTAL);
		soundSlider.setIndicatorPosition(IndicatorPosition.RIGHT);

		setQuickToolTip(soundSlider, "Adjust Sound Volume"); //$NON-NLS-1$

        Label header_label = new Label("SOUND VOLUME");
        header_label.setStyle("-fx-text-fill: black;"
        			+ "-fx-font-size: 12px;"
        		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
        			+ "-fx-font-weight: normal;");
        header_label.setPadding(new Insets(3, 0, 1, 10));

		// detect dragging
        soundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {

            	if (old_val != new_val) {

	            	float sliderValue = new_val.floatValue();
	            	//System.out.println("sliderValue : " + sliderValue);

	            	if (sliderValue <= 0) {
				        soundPlayer.setMute(true);
					}
					else {
						soundPlayer.setMute(false);
						soundPlayer.setVolume((float) convertSlider2Volume(sliderValue));
					}
            	}
            }
        });

        VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(header_label, soundSlider);
        soundPane.getChildren().addAll(vBox);

	}


	public void createMarsTimeBar() {
		marsTimeButton = new Button();

		marsTimeButton.setMaxWidth(Double.MAX_VALUE);
		if (OS.contains("linux")) {
			marsTimeButton.setMinWidth(LINUX_WIDTH);
			marsTimeButton.setPrefSize(LINUX_WIDTH, 29);
		}
		else if (OS.contains("mac")) {
			marsTimeButton.setMinWidth(MACOS_WIDTH);
			marsTimeButton.setPrefSize(MACOS_WIDTH, 28);
		}
		else {
			marsTimeButton.setMinWidth(WIN_WIDTH);
			marsTimeButton.setPrefSize(WIN_WIDTH, 33);
		}

		//marsTimeBar = new HBox();
		//marsTimeBar.setId("rich-orange");
/*
		marsTimeBar.setMaxWidth(Double.MAX_VALUE);
		if (OS.contains("linux")) {
			marsTimeBar.setMinWidth(LINUX_WIDTH);
			marsTimeBar.setPrefSize(LINUX_WIDTH, 32);
		}
		else if (OS.contains("macos")) {
			marsTimeBar.setMinWidth(MACOS_WIDTH);
			marsTimeBar.setPrefSize(MACOS_WIDTH, 32);
		}
		else {
			marsTimeBar.setMinWidth(WIN_WIDTH);
			marsTimeBar.setPrefSize(WIN_WIDTH, 32);
		}
*/
		if (masterClock == null) {
			masterClock = sim.getMasterClock();
		}

		if (marsClock == null) {
			marsClock = masterClock.getMarsClock();
		}


		calendarDisplay = new MarsCalendarDisplay(marsClock, desktop);

		SwingNode calNode = new SwingNode();
		calNode.setContent(calendarDisplay);

        Label header_label = new Label("MARTIAN CALENDAR");
        header_label.setStyle("-fx-text-fill: black;"
        			+ "-fx-font-size: 12px;"
        		    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
        			+ "-fx-font-weight: normal;");
        header_label.setPadding(new Insets(0, 0, 1, 0));

		monthLabel = new Label("Month : " + marsClock.getMonthName());
		monthLabel.setPadding(new Insets(2, 0, 2, 5));
		monthLabel.setStyle("-fx-background-color: linear-gradient(to bottom, -fx-base, derive(-fx-base,30%));"
    			+ "-fx-font-size: 12px;"
				+ "-fx-text-fill: #654b00;");

		yearLabel = new Label("Year : " + marsClock.getOrbitString());
		yearLabel.setPadding(new Insets(2, 5, 2, 0));
		yearLabel.setStyle("-fx-background-color: linear-gradient(to bottom, -fx-base, derive(-fx-base,30%));"
    			+ "-fx-font-size: 12px;"
				+ "-fx-text-fill: #654b00;");

		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(yearLabel, monthLabel);

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(header_label, hBox, calNode);

		calendarPane = new StackPane(vBox);
		calendarPane.getStyleClass().add("jfx-popup-container");
		calendarPane.setAlignment(Pos.CENTER);
		calendarPane.setPrefHeight(170);
		calendarPane.setPrefWidth(180);
		calendarPane.setPadding(new Insets(5, 5, 10, 5));

		marsCalendarPopup = new JFXPopup(calendarPane);
		//marsTimeButton = new Button();//Label();
		//marsTimeButton.setMaxWidth(Double.MAX_VALUE);
		setQuickToolTip(marsTimeButton, "Click to open Martian calendar");
		marsTimeButton.setOnAction(e -> {
			//if (marsTimeFlag) {
				// TODO more here
			//	marsTimeFlag = false;
			//}
			//else {
				// TODO more here
			//	marsTimeFlag = true;
			//}
            if (marsCalendarPopup.isShowing()) {
            	marsCalendarPopup.hide();//close();
            }
            else {
            	marsCalendarPopup.show(marsTimeButton, PopupVPosition.TOP, PopupHPosition.RIGHT, -20, 25);
            }
		});

		//marsCalendarPopup.setContent(calendarPane);
		//marsCalendarPopup.setPopupContainer(rootAnchorPane);
		//marsCalendarPopup.setSource(marsTimeButton);

		marsTimeButton.setId("rich-orange");
		//marsTimeButton.setTextAlignment(TextAlignment.LEFT);
		marsTimeButton.setAlignment(Pos.CENTER_LEFT);
		//setQuickToolTip(marsTime, "Click to see Quick Info on Mars");

		//northHemi = new Button("\u25D2");
		//northHemi.setId("button-orange");
		//northHemi.setStyle("-fx-text-fill: black;"
    	//		+ "-fx-font-size: 24px;"
    	//	    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
    	//		+ "-fx-font-weight: bold;");
		//northHemi.setTextAlignment(TextAlignment.CENTER);

		//southHemi = new Button("\u25D3");
		//southHemi.setId("button-orange");
		//southHemi.setStyle("-fx-text-fill: black;"
    	//		+ "-fx-font-size: 24px;"
    	//	    + "-fx-text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;"
    	//		+ "-fx-font-weight: bold;");
		//southHemi.setTextAlignment(TextAlignment.CENTER);

		//marsTimeBar.getChildren().add(marsTimeButton);//addAll(northHemi, southHemi, marsTimeButton);
	}

	public void createFXButtons() {

		minimapToggle = new JFXToggleButton();
		//pinButton.setTextFill(Paint.OPAQUE);
		minimapToggle.setText("Minimap Off");
		minimapToggle.setSelected(false);
		setQuickToolTip(minimapToggle, "Pin Minimap");
		minimapToggle.setOnAction(e -> {
			if (minimapToggle.isSelected()) {
				openMinimap();
			}
			else {
				closeMinimap();
				//minimapToggle.setText("Minimap Off");
				//desktop.closeToolWindow(NavigatorWindow.NAME);
				//mapAnchorPane.getChildren().remove(minimapStackPane);
	    	    //minimapButton.setSelected(false);
			}

			//minimapButton.toFront();

		});

		mapToggle = new JFXToggleButton();
		mapToggle.setText("Settlement Map Off");
		mapToggle.setSelected(false);
		setQuickToolTip(mapToggle, "Pin Settlement Map");
		mapToggle.setOnAction(e -> {
			if (mapToggle.isSelected()) {
				if (!desktop.isToolWindowOpen(SettlementWindow.NAME))
					openSettlementMap();
				if (desktop.isToolWindowOpen(NavigatorWindow.NAME)) {
					//pinButton.setSelected(true);
					//closeMinimap();
					openMinimap();
				}
			}
			else {
				closeSettlementMap();
			}

			//sMapButton.toFront();

		});

		cacheToggle = new JFXToggleButton();
		cacheToggle.setText("Map Cache Off");
		cacheToggle.setSelected(false);
		setQuickToolTip(cacheToggle, "Retain Settlement Map and Minimap after switching to another tab");
		cacheToggle.setOnAction(e -> {
			if (cacheToggle.isSelected()) {
				cacheToggle.setText("Map Cache On");
				if (!desktop.isToolWindowOpen(SettlementWindow.NAME))
					openSettlementMap();
				if (!desktop.isToolWindowOpen(NavigatorWindow.NAME)) {
					//pinButton.setSelected(true);
					openMinimap();
				}
				else
		    	    minimapStackPane.toFront();

				minimapToggle.toFront();
				mapToggle.toFront();
			}
			else
				cacheToggle.setText("Map Cache Off");
		});

		rotateCWBtn = new JFXButton();
		rotateCWBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream(Msg.getString("img.cw")))));	 //$NON-NLS-1$
		//Tooltip t0 = new Tooltip(Msg.getString("SettlementTransparentPanel.tooltip.clockwise")); //$NON-NLS-1$
		//rotateCWBtn.setTooltip(t0);
		setQuickToolTip(rotateCWBtn, Msg.getString("SettlementTransparentPanel.tooltip.clockwise"));
		rotateCWBtn.setOnAction(e -> {
			mapPanel.setRotation(mapPanel.getRotation() + ROTATION_CHANGE);
		});

		rotateCCWBtn = new JFXButton();
		rotateCCWBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream(Msg.getString("img.ccw")))));	//$NON-NLS-1$
		//Tooltip t1 = new Tooltip(Msg.getString("SettlementTransparentPanel.tooltip.counterClockwise")); //$NON-NLS-1$
		//rotateCCWBtn.setTooltip(t1);
		setQuickToolTip(rotateCCWBtn, Msg.getString("SettlementTransparentPanel.tooltip.counterClockwise"));
		rotateCCWBtn.setOnAction(e -> {
			mapPanel.setRotation(mapPanel.getRotation() - ROTATION_CHANGE);
		});

		recenterBtn = new JFXButton();
		recenterBtn.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream(Msg.getString("img.recenter"))))); //$NON-NLS-1$
		//Tooltip t2 = new Tooltip(Msg.getString("SettlementTransparentPanel.tooltip.recenter")); //$NON-NLS-1$
		//recenterBtn.setTooltip(t2);
		setQuickToolTip(recenterBtn, Msg.getString("SettlementTransparentPanel.tooltip.recenter"));
		recenterBtn.setOnAction(e -> {
			mapPanel.reCenter();
			zoomSlider.setValue(0);
		});

	}

	public void createFXZoomSlider() {
		//logger.info("MainScene's createFXZoomSlider() is on " + Thread.currentThread().getName() + " Thread");

		// Set up a settlement view zoom bar
		zoomSlider = new JFXSlider();
		//zoomSlider.getStyleClass().add("jfx-slider");
		//zoom.setMinHeight(100);
		//zoom.setMaxHeight(200);
		zoomSlider.prefHeightProperty().bind(mapStackPane.heightProperty().multiply(.3d));
		zoomSlider.setMin(-10);
		zoomSlider.setMax(10);
		zoomSlider.setValue(0);
		zoomSlider.setMajorTickUnit(5);
		zoomSlider.setShowTickLabels(true);
		zoomSlider.setShowTickMarks(true);
		zoomSlider.setBlockIncrement(1);
		zoomSlider.setOrientation(Orientation.VERTICAL);
		zoomSlider.setIndicatorPosition(IndicatorPosition.RIGHT);

		setQuickToolTip(zoomSlider, Msg.getString("SettlementTransparentPanel.tooltip.zoom")); //$NON-NLS-1$

		// detect dragging on zoom scroll bar
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	// Change scale of map based on slider position.
				int sliderValue = (int) new_val.doubleValue();
				double defaultScale = SettlementMapPanel.DEFAULT_SCALE;
				double newScale = defaultScale;
				if (sliderValue > 0) {
					newScale += defaultScale * (double) sliderValue * SettlementTransparentPanel.ZOOM_CHANGE;
				}
				else if (sliderValue < 0) {
					newScale = defaultScale / (1D + ((double) sliderValue * -1D * SettlementTransparentPanel.ZOOM_CHANGE));
				}
				mapPanel.setScale(newScale);
            }
        });
	}


	public void createFXSettlementComboBox() {
		sBox = new JFXComboBox<>();
		//sBox.setAlignment(Pos.CENTER_RIGHT);
		//JFXListView<Settlement> list = new JFXListView<Settlement>();
		sBox.getStyleClass().add("jfx-combo-box");
		setQuickToolTip(sBox, Msg.getString("SettlementWindow.tooltip.selectSettlement")); //$NON-NLS-1$
		//ObservableList<Settlement> names = sim.getUnitManager().getSettlementOList();
		sBox.itemsProperty().setValue(sim.getUnitManager().getSettlementOList());
		sBox.setPromptText("Select a settlement to view");
		sBox.getSelectionModel().selectFirst();

		sBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue) {
				SwingUtilities.invokeLater(() -> mapPanel.setSettlement((Settlement)newValue));
			}
    	});


		settlementBox = new StackPane(sBox);
		settlementBox.setMaxSize(180, 30);
		settlementBox.setPrefSize(180, 30);
		settlementBox.setAlignment(Pos.CENTER_RIGHT);

	}

	public void changeSBox() {
		sBox.itemsProperty().setValue(sim.getUnitManager().getSettlementOList());
	}

	public void createFXMapLabelBox() {

		mapLabelBox = new VBox();
		mapLabelBox.setSpacing(5);
		mapLabelBox.setMaxSize(180, 150);
		mapLabelBox.setPrefSize(180, 150);
		//mapLabelBox.setAlignment(Pos.CENTER_RIGHT);

		JFXCheckBox box0 = new JFXCheckBox(Msg.getString("SettlementWindow.menu.daylightTracking"));
		JFXCheckBox box1 = new JFXCheckBox(Msg.getString("SettlementWindow.menu.buildings"));
		JFXCheckBox box2 = new JFXCheckBox(Msg.getString("SettlementWindow.menu.constructionSites"));
		JFXCheckBox box3 = new JFXCheckBox(Msg.getString("SettlementWindow.menu.people"));
		JFXCheckBox box4 = new JFXCheckBox(Msg.getString("SettlementWindow.menu.robots"));
		JFXCheckBox box5 = new JFXCheckBox(Msg.getString("SettlementWindow.menu.vehicles"));

		mapLabelBox.getChildren().addAll(box0, box1, box2, box3, box4, box5);

		box0.setSelected(mapPanel.isDaylightTrackingOn());
		box1.setSelected(mapPanel.isShowBuildingLabels());
		box2.setSelected(mapPanel.isShowConstructionLabels());
		box3.setSelected(mapPanel.isShowPersonLabels());
		box4.setSelected(mapPanel.isShowRobotLabels());
		box5.setSelected(mapPanel.isShowVehicleLabels());

		box0.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if (oldValue != newValue) {
			    	box0.setSelected(newValue);
					mapPanel.setShowDayNightLayer(newValue);
		        }
		    }
		});

		box1.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if (oldValue != newValue) {
			    	box1.setSelected(newValue);
					mapPanel.setShowBuildingLabels(newValue);
		        }
		    }
		});

		box2.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if (oldValue != newValue) {
			    	box2.setSelected(newValue);
					mapPanel.setShowConstructionLabels(newValue);
		        }
		    }
		});

		box3.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if (oldValue != newValue) {
			    	box3.setSelected(newValue);
					mapPanel.setShowPersonLabels(newValue);
		        }
		    }
		});

		box4.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if (oldValue != newValue) {
			    	box4.setSelected(newValue);
					mapPanel.setShowRobotLabels(newValue);
		        }
		    }
		});

		box5.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        if (oldValue != newValue) {
			    	box5.setSelected(newValue);
					mapPanel.setShowVehicleLabels(newValue);
		        }
		    }
		});
	}

	/**
	 * Creates the tab pane for housing a bunch of tabs
	 */
	@SuppressWarnings("restriction")
	public void createJFXTabs() {
		//logger.info("MainScene's createJFXTabs() is on " + Thread.currentThread().getName() + " Thread");

		jfxTabPane = new JFXTabPane();
		jfxTabPane.setPrefSize(sceneHeight.get(),sceneWidth.get());

		String cssFile = null;

        if (theme == 0 || theme == 6)
        	cssFile = "/fxui/css/jfx_blue.css";
        else
        	cssFile = "/fxui/css/jfx_orange.css";

		jfxTabPane.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
		jfxTabPane.getStyleClass().add("jfx-tab-pane");

		mainTab = new Tab();
		mainTab.setText("Main");
		mainTab.setContent(mainAnchorPane);

		// set up mapTab
		mapAnchorPane = new AnchorPane();
		mapAnchorPane.setStyle("-fx-background-color: black; ");

		Tab mapTab = new Tab();
		mapTab.setText("Map");
		mapTab.setContent(mapAnchorPane);

		navWin = (NavigatorWindow) desktop.getToolWindow(NavigatorWindow.NAME);

		minimapNode = new SwingNode();
		minimapStackPane = new StackPane(minimapNode);
		minimapNode.setContent(navWin);
		minimapStackPane.setStyle("-fx-background-color: black; ");
		minimapNode.setStyle("-fx-background-color: black; ");
/*
		miniMapBtn = new JFXButton();
		setQuickToolTip(miniMapBtn, "Open Mars Navigator Minimap below");
		miniMapBtn.setOnAction(e -> {

			if (desktop.isToolWindowOpen(NavigatorWindow.NAME)) {
				desktop.closeToolWindow(NavigatorWindow.NAME);
				mapAnchorPane.getChildren().remove(minimapStackPane);
				minimapButton.setSelected(false);
				minimapButton.setText("Minimap Off");
			}

			else {
				openMinimap();
			}
		});
*/
		settlementWindow = (SettlementWindow) desktop.getToolWindow(SettlementWindow.NAME);
		mapPanel = settlementWindow.getMapPanel();

		mapNode = new SwingNode();
		mapStackPane = new StackPane(mapNode);
		mapNode.setContent(settlementWindow);
		mapStackPane.setStyle("-fx-background-color: black; ");
		mapNode.setStyle("-fx-background-color: black; ");

		createFXButtons();
		createFXSettlementComboBox();
		createFXZoomSlider();
		createFXMapLabelBox();

        // detect mouse wheel scrolling
        mapStackPane.setOnScroll(new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent event) {

                if (event.getDeltaY() == 0)
                    return;

 				double direction = event.getDeltaY();

				if (direction > 0) {
					// Move zoom slider down.
					if (zoomSlider.getValue() > zoomSlider.getMin())
						zoomSlider.setValue( (zoomSlider.getValue() - 1));
				}
				else if (direction < 0) {
					// Move zoom slider up.
					if (zoomSlider.getValue() < zoomSlider.getMax())
						zoomSlider.setValue( (zoomSlider.getValue() + 1));
				}
                //event.consume();
            }
        });

		//desktop.openToolWindow(MonitorWindow.NAME);
		//desktop.openToolWindow(MissionWindow.NAME);
		//desktop.openToolWindow(ResupplyWindow.NAME);
		//desktop.openToolWindow(ScienceWindow.NAME);


		// set up monitor tab
		//MonitorWindow monWin = (MonitorWindow) desktop.getToolWindow(MonitorWindow.NAME);
		//monNode = new SwingNode();
		//monNode.setContent(monWin);
		//monPane = new StackPane(monNode);

		//desktop.openToolWindow(MonitorWindow.NAME);

/*
 *
		// set up mission tab
		MissionWindow missionWin = (MissionWindow) desktop.getToolWindow(MissionWindow.NAME);
		missionNode = new SwingNode();
	    JDesktopPane d1 = desktops.get(1);
	    d1.add(missionWin);
		missionNode.setContent(d1);
		StackPane missionPane = new StackPane(missionNode);
		Tab missionTab = new Tab();
		missionTab.setText("Mission");
		missionTab.setContent(missionPane);

		//desktop.openToolWindow(MissionWindow.NAME);


		// set up resupply tab
		ResupplyWindow resupplyWin = (ResupplyWindow) desktop.getToolWindow(ResupplyWindow.NAME);
		resupplyNode = new SwingNode();
	    JDesktopPane d2 = desktops.get(2);
	    d2.add(resupplyWin);
		resupplyNode.setContent(d2);
		StackPane resupplyPane = new StackPane(resupplyNode);
		Tab resupplyTab = new Tab();
		resupplyTab.setText("Resupply");
		resupplyTab.setContent(resupplyPane);

		//desktop.openToolWindow(ResupplyWindow.NAME);

		// set up science tab
		ScienceWindow sciWin = (ScienceWindow) desktop.getToolWindow(ScienceWindow.NAME);
		sciNode = new SwingNode();
		// Note: don't need to create a DesktopPane for scienceWin
	    //JDesktopPane d4 = desktops.get(4);
	    //d4.add(scienceWin);
		//scienceNode.setContent(d4);
		sciNode.setContent(sciWin);
		StackPane sciencePane = new StackPane(sciNode);
		Tab scienceTab = new Tab();
		scienceTab.setText("Science");
		scienceTab.setContent(sciencePane);

		//desktop.openToolWindow(ScienceWindow.NAME);
*/

		// set up help tab
		GuideWindow guideWin = (GuideWindow) desktop.getToolWindow(GuideWindow.NAME);
		guideNode = new SwingNode();
		guideNode.setContent(guideWin);
		StackPane guidePane = new StackPane(guideNode);
		Tab guideTab = new Tab();
		guideTab.setText("Help");
		guideTab.setContent(guidePane);

		//desktop.openToolWindow(GuideWindow.NAME);

		jfxTabPane.getTabs().addAll(
				mainTab,
				mapTab,
				//monTab,
				//missionTab,
				//resupplyTab,
				//scienceTab,
				guideTab);


		jfxTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {

			if (newTab == mainTab) {
				mainAnchorPane.requestFocus();
				closeMaps();
				desktop.closeToolWindow(GuideWindow.NAME);
				//anchorDesktopPane.getChildren().removeAll(miniMapBtn, mapBtn);
				//anchorMapTabPane.getChildren().removeAll(cacheButton);
			}
/*
			else if (newTab == monTab) {
				if (!desktop.isToolWindowOpen(MonitorWindow.NAME)) {
					desktop.openToolWindow(MonitorWindow.NAME);
					//monNode.setContent(monWin);
				}

				anchorDesktopPane.getChildren().removeAll(miniMapBtn, mapBtn);
				anchorMapTabPane.getChildren().removeAll(cacheButton);

			}
*/
			else if (newTab == mapTab) {

/*
				if (!desktop.isToolWindowOpen(SettlementWindow.NAME)) {
					System.out.println("settlement map was closed");
					if (isCacheButtonOn())
						mapBtn.fire();
				}

				if (!desktop.isToolWindowOpen(NavigatorWindow.NAME)) {
					System.out.println("minimap was closed");
					if (isCacheButtonOn()) {
						miniMapBtn.fire();
						if (nw == null) nw = (NavigatorWindow) desktop.getToolWindow(NavigatorWindow.NAME);
						nw.getGlobeDisplay().drawSphere();//updateDisplay();
					}
				}
*/

/*
				AnchorPane.setRightAnchor(mapBtn, 125.0);
				if (OS.contains("win"))
					AnchorPane.setTopAnchor(mapBtn, 0.0);
				else
					AnchorPane.setTopAnchor(mapBtn, -3.0);
				rootAnchorPane.getChildren().addAll(mapBtn);

		        AnchorPane.setRightAnchor(miniMapBtn, 165.0);
				if (OS.contains("win"))
					AnchorPane.setTopAnchor(miniMapBtn, 0.0);
				else
					AnchorPane.setTopAnchor(miniMapBtn, -3.0);
				rootAnchorPane.getChildren().addAll(miniMapBtn);
*/
		        AnchorPane.setRightAnchor(cacheToggle, 25.0);
		        AnchorPane.setTopAnchor(cacheToggle, 45.0);  // 55.0

		        AnchorPane.setLeftAnchor(minimapToggle, 10.0);
		        AnchorPane.setTopAnchor(minimapToggle, 10.0);  // 55.0

		        AnchorPane.setRightAnchor(mapToggle, 15.0);
		        AnchorPane.setTopAnchor(mapToggle, 10.0);  // 55.0

				mapAnchorPane.getChildren().addAll(cacheToggle, minimapToggle, mapToggle);

				desktop.closeToolWindow(GuideWindow.NAME);

				//rootAnchorPane.getChildren().remove(monPane);
				//desktop.closeToolWindow(MonitorWindow.NAME);
			}

			else if (newTab == guideTab) {

				if (!desktop.isToolWindowOpen(GuideWindow.NAME))
					desktop.openToolWindow(GuideWindow.NAME);

				closeMaps();
				//anchorDesktopPane.getChildren().removeAll(miniMapBtn, mapBtn);
			    //anchorMapTabPane.getChildren().removeAll(cacheButton);
			}
/*
			else if (newTab == missionTab) {
				if (!desktop.isToolWindowOpen(MissionWindow.NAME)) {
					desktop.openToolWindow(MissionWindow.NAME);
					//missionNode.setContent(missionWin);
				}

				anchorDesktopPane.getChildren().removeAll(miniMapBtn, mapBtn);
			    anchorMapTabPane.getChildren().removeAll(cacheButton);
			}

			else if (newTab == resupplyTab) {
				if (!desktop.isToolWindowOpen(ResupplyWindow.NAME)) {
					desktop.openToolWindow(ResupplyWindow.NAME);
					//resupplyNode.setContent(resupplyWin);
				}

				anchorDesktopPane.getChildren().removeAll(miniMapBtn, mapBtn);
			    anchorMapTabPane.getChildren().removeAll(cacheButton);
			}

			else if (newTab == scienceTab) {
				if (!desktop.isToolWindowOpen(ScienceWindow.NAME)) {
					desktop.openToolWindow(ScienceWindow.NAME);
					//sciNode.setContent(scienceWin);
				}

				anchorDesktopPane.getChildren().removeAll(miniMapBtn, mapBtn);
			    anchorMapTabPane.getChildren().removeAll(cacheButton);
			}
*/
			else {
				//rootAnchorPane.getChildren().removeAll(miniMapBtn, mapBtn);
			    mapAnchorPane.getChildren().removeAll(cacheToggle, minimapToggle, mapToggle);
			}

		});

		//jfxTabPane.getSelectionModel().select(guideTab);

		// NOTE: if a tab is NOT selected, should close that tool as well to save cpu utilization
		// this is done in ToolWindow's update(). It allows for up to 1 second of delay, in case user open and close the same repeated.

	}

	public void openMinimap() {
		//if (!desktop.isToolWindowOpen(NavigatorWindow.NAME)) {
			desktop.openToolWindow(NavigatorWindow.NAME);
			//minimapNode.setContent(navWin);
		    AnchorPane.setLeftAnchor(minimapStackPane, 3.0);
		    AnchorPane.setTopAnchor(minimapStackPane, 0.0); // 45.0
		    boolean flag = false;
		    for (Node node : mapAnchorPane.getChildrenUnmodifiable()) {
		    	if (node == minimapStackPane) {
		    		flag = true;
		    		break;
		    	}
		    }
	    	if (!flag)
	    		mapAnchorPane.getChildren().addAll(minimapStackPane);
			navWin.getGlobeDisplay().drawSphere();//updateDisplay();
    	    navWin.toFront();
    	    navWin.requestFocus();
    	    minimapStackPane.toFront();
    	    minimapToggle.setSelected(true);
			minimapToggle.setText("Minimap On");
    	    minimapToggle.toFront();
		//}
	}

	public void openSettlementMap() {

		mapStackPane.prefWidthProperty().unbind();
		mapStackPane.prefWidthProperty().bind(scene.widthProperty().subtract(1));

		desktop.openToolWindow(SettlementWindow.NAME);
		//mapNode.setContent(settlementWindow);

        AnchorPane.setRightAnchor(mapStackPane, 0.0);
        AnchorPane.setTopAnchor(mapStackPane, 0.0);

        AnchorPane.setRightAnchor(zoomSlider, 65.0);
        AnchorPane.setTopAnchor(zoomSlider, 350.0);//(mapNodePane.heightProperty().get() - zoomSlider.heightProperty().get())*.4d);

        AnchorPane.setRightAnchor(rotateCWBtn, 110.0);
        AnchorPane.setTopAnchor(rotateCWBtn, 300.0);

        AnchorPane.setRightAnchor(rotateCCWBtn, 30.0);
        AnchorPane.setTopAnchor(rotateCCWBtn, 300.0);

        AnchorPane.setRightAnchor(recenterBtn, 70.0);
        AnchorPane.setTopAnchor(recenterBtn, 300.0);

        AnchorPane.setRightAnchor(settlementBox, 15.0);//anchorMapTabPane.widthProperty().get()/2D - 110.0);//settlementBox.getWidth());
        AnchorPane.setTopAnchor(settlementBox, 100.0);

        AnchorPane.setRightAnchor(mapLabelBox, -10.0);
        AnchorPane.setTopAnchor(mapLabelBox, 140.0);


        boolean hasMap = false, hasZoom = false, hasButtons = false,
        		hasSettlements = false, hasMapLabel = false;

        ObservableList<Node> nodes = mapAnchorPane.getChildrenUnmodifiable();

        for (Node node : nodes) {

	        if (node == settlementBox) {
	        	hasSettlements = true;
	        }
	        else if (node == mapStackPane) {
	        	hasMap = true;
	        }
	        else if (node == zoomSlider) {
	        	hasZoom = true;
	        }
	        else if (node == recenterBtn || node == rotateCWBtn || node == rotateCCWBtn) {
	        	hasButtons = true;
	        }
	        else if (node == mapLabelBox)
	        	hasMapLabel = true;

		}

		if (!hasMap)
			mapAnchorPane.getChildren().addAll(mapStackPane);

		if (!hasSettlements)
			mapAnchorPane.getChildren().addAll(settlementBox);

		if (!hasMapLabel)
			mapAnchorPane.getChildren().addAll(mapLabelBox);

		if (!hasZoom)
			mapAnchorPane.getChildren().addAll(zoomSlider);

		if (!hasButtons)
			mapAnchorPane.getChildren().addAll(rotateCWBtn, rotateCCWBtn, recenterBtn);
/*
			for (Node node : mapAnchorPane.getChildrenUnmodifiable()) {
		        if (node == cacheButton) {
		        	node.toFront();
		        }
		        else if (node == minimapButton) {
		        	node.toFront();
		        }
		        else if (node == sMapButton) {
		        	node.toFront();
		        }
		        else if (node == settlementBox) {
		        	node.toFront();
		        }
		        else if (node == mapLabelBox) {
		        	node.toFront();
		        }
		    }
*/
		mapLabelBox.toFront();
		settlementBox.toFront();
		mapToggle.toFront();
		cacheToggle.toFront();
		minimapToggle.toFront();

		mapToggle.setText("Settlement Map On");
		mapToggle.setSelected(true);

	}

	public void closeMinimap() {
		desktop.closeToolWindow(NavigatorWindow.NAME);
		Platform.runLater(() -> {
			mapAnchorPane.getChildren().remove(minimapStackPane);
			minimapToggle.setSelected(false);
			minimapToggle.setText("Minimap Off");
		    jfxTabPane.requestFocus();
		});
		//System.out.println("closing minimap...");
	}

	public void closeSettlementMap() {
		desktop.closeToolWindow(SettlementWindow.NAME);
		Platform.runLater(() -> {
			mapAnchorPane.getChildren().removeAll(mapStackPane,
					settlementBox, mapLabelBox,
					zoomSlider,
					rotateCWBtn, rotateCCWBtn, recenterBtn);
			mapToggle.setSelected(false);
			mapToggle.setText("Settlement Map Off");
			jfxTabPane.requestFocus();
		});
	}

	public void closeMaps() {
	    mapAnchorPane.getChildren().removeAll(cacheToggle, minimapToggle, mapToggle);
	    if (!isCacheButtonOn()) {
			desktop.closeToolWindow(SettlementWindow.NAME);
			desktop.closeToolWindow(NavigatorWindow.NAME);
			Platform.runLater(() -> {
				mapAnchorPane.getChildren().removeAll(minimapStackPane, mapStackPane, zoomSlider, rotateCWBtn, rotateCCWBtn, recenterBtn, settlementBox, mapLabelBox);
				//System.out.println("closing both maps...");
				minimapToggle.setSelected(false);
				minimapToggle.setText("Minimap Off");
				mapToggle.setSelected(false);
				mapToggle.setText("Settlement Map Off");
			});
		}
	    jfxTabPane.requestFocus(); // rootAnchorPane //jfxTabPane
	}


	public boolean isCacheButtonOn() {
		if (cacheToggle.isSelected())
			return true;
		else
			return false;
	}


	/*
	 * Sets the theme skin after calling stage.show() at the start of the sim
	 */
	public void initializeTheme() {
		//logger.info("MainScene's initializeTheme()");
		// NOTE: it is mandatory to change the theme from 1 to 2 below at the start of the sim
		// This avoids two display issues:
		// (1). the crash of Mars Navigator Tool when it was first loaded
		// (2). the inability of loading the tab icons of the Monitor Tool at the beginning
		// Also, when clicking a tab at the first time, a NullPointerException results)
		// TODO: find out if it has to do with nimrodlf and/or JIDE-related
		if (OS.contains("linux"))
			setTheme(0);
		else
			setTheme(7);
		//logger.info("done with MainScene's initializeTheme()");
	}

	/*
	 * Sets the theme skin of the desktop
	 */
	public void setTheme(int theme) {

		if (menuBar.getStylesheets() != null)
			menuBar.getStylesheets().clear();

		String cssFile;

		if (this.theme != theme) {
			this.theme = theme;

			if (theme == 0) { //  snow blue
				// for numbus theme
				cssFile = "/fxui/css/snowBlue.css";
				updateThemeColor(0, Color.rgb(0,107,184), Color.rgb(0,107,184), cssFile); // CADETBLUE // Color.rgb(23,138,255)
				themeSkin = "snowBlue";

			} else if (theme == 1) { // olive green
				cssFile = "/fxui/css/oliveskin.css";
				updateThemeColor(1, Color.GREEN, Color.PALEGREEN, cssFile); //DARKOLIVEGREEN
				themeSkin = "LightTabaco";

			} else if (theme == 2) { // burgundy red
				cssFile = "/fxui/css/burgundyskin.css";
				updateThemeColor(2, Color.rgb(140,0,26), Color.YELLOW, cssFile); // ORANGERED
				themeSkin = "Burdeos";

			} else if (theme == 3) { // dark chocolate
				cssFile = "/fxui/css/darkTabaco.css";
				updateThemeColor(3, Color.DARKGOLDENROD, Color.BROWN, cssFile);
				themeSkin = "DarkTabaco";

			} else if (theme == 4) { // grey
				cssFile = "/fxui/css/darkGrey.css";
				updateThemeColor(4, Color.DARKSLATEGREY, Color.DARKGREY, cssFile);
				themeSkin = "DarkGrey";

			} else if (theme == 5) { // + purple
				cssFile = "/fxui/css/nightViolet.css";
				updateThemeColor(5, Color.rgb(73,55,125), Color.rgb(73,55,125), cssFile); // DARKMAGENTA, SLATEBLUE
				themeSkin = "Night";

			} else if (theme == 6) { // + skyblue

				cssFile = "/fxui/css/snowBlue.css";
				updateThemeColor(6, Color.rgb(0,107,184), Color.rgb(255,255,255), cssFile); //(144, 208, 229) light blue // CADETBLUE (0,107,184)// Color.rgb(23,138,255)
				themeSkin = "snowBlue";

			} else if (theme == 7) { // mud orange/standard

				cssFile = "/fxui/css/nimrodskin.css";
				updateThemeColor(7, Color.rgb(156,77,0), Color.rgb(255,255,255), cssFile); //DARKORANGE, CORAL
				themeSkin = "nimrod";

			}

			SwingUtilities.invokeLater(() -> {
				// 2016-06-17 Added checking for OS.
				if (OS.contains("linux")) {
					// Note: NIMROD theme lib doesn't work on linux
					setLookAndFeel(NIMBUS_THEME);
				}
				else
					setLookAndFeel(NIMROD_THEME);
			});
		}
		//logger.info("done with MainScene's changeTheme()");
	}

	/**
	 * Sets the look and feel of the UI
	 * @param nativeLookAndFeel true if native look and feel should be used.
	 */
	// 2015-05-02 Edited setLookAndFeel()
	public void setLookAndFeel(int choice) {
		//logger.info("MainScene's setLookAndFeel() is on " + Thread.currentThread().getName() + " Thread");
		boolean changed = false;
		if (choice == SYSTEM_THEME) { // theme == "nativeLookAndFeel"
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				changed = true;
				//themeSkin = "system";
				//System.out.println("found system");
			} catch (Exception e) {
				logger.log(Level.WARNING, Msg.getString("MainWindow.log.lookAndFeelError"), e); //$NON-NLS-1$
			}
		} else if (choice == NIMROD_THEME) { // theme == "nimRODLookAndFeel"
			try {
				NimRODTheme nt = new NimRODTheme(getClass().getClassLoader().getResource("theme/" + themeSkin + ".theme"));
				//NimRODLookAndFeel.setCurrentTheme(nt); // must be declared non-static or not working if switching to a brand new .theme file
				NimRODLookAndFeel nf = new NimRODLookAndFeel();
				nf.setCurrentTheme(nt); //must be declared non-static or not working if switching to a brand new .theme file
				UIManager.setLookAndFeel(nf);
				changed = true;
				//System.out.println("found Nimrod");

			} catch (Exception e) {
				logger.log(Level.WARNING, Msg.getString("MainWindow.log.lookAndFeelError"), e); //$NON-NLS-1$
			}
		} else if (choice == NIMBUS_THEME) {
			try {
				boolean foundNimbus = false;
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if (info.getName().equals("Nimbus")) {
						// Set Nimbus look & feel if found in JVM.
						//System.out.println("found Nimbus");
						UIManager.setLookAndFeel(info.getClassName());
						foundNimbus = true;
						//themeSkin = "nimbus";
						changed = true;
						//break;
					}
				}

				// Metal Look & Feel fallback if Nimbus not present.
				if (!foundNimbus) {
					logger.log(Level.WARNING, Msg.getString("MainWindow.log.nimbusError")); //$NON-NLS-1$
					UIManager.setLookAndFeel(new MetalLookAndFeel());
					//themeSkin = "metal";
					changed = true;
					//System.out.println("found metal");
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, Msg.getString("MainWindow.log.nimbusError")); //$NON-NLS-1$
			}
		}

		if (changed) {
			if (desktop != null) {
				desktop.updateToolWindowLF();
				desktop.updateUnitWindowLF();
				SwingUtilities.updateComponentTreeUI(desktop);
				//desktop.updateAnnouncementWindowLF();
				// desktop.updateTransportWizardLF();
				//System.out.println("just updated UI");
			}
		}
		//logger.info("MainScene's setLookAndFeel() is on " + Thread.currentThread().getName() + " Thread");
	}

	/*
	 * Updates the theme colors of statusBar, swingPane and menuBar
	 */
	// 2015-08-29 Added updateThemeColor()
	public void updateThemeColor(int theme, Color txtColor, Color txtColor2, String cssFile) {
		mainAnchorPane.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
		if (!OS.contains("mac"))
			menuBar.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());

		// Note : menu bar color
		// orange theme : F4BA00
		// blue theme : 3291D2

		//String color = txtColor.replace("0x", "");

		jfxTabPane.getStylesheets().clear();

		setStylesheet(marsTimeButton, cssFile);
		setStylesheet(earthTimeButton, cssFile);

		setStylesheet(lastSaveLabel, cssFile);
		setStylesheet(cacheToggle, cssFile);
		setStylesheet(minimapToggle, cssFile);
		setStylesheet(mapToggle, cssFile);

		setStylesheet(settlementBox, cssFile);
		setStylesheet(mapLabelBox, cssFile);

		setStylesheet(speedPane, cssFile);
		setStylesheet(calendarPane, cssFile);
		setStylesheet(soundPane, cssFile);

		setStylesheet(timeSlider, cssFile);
		//setStylesheet(zoomSlider, cssFile);
		setStylesheet(soundSlider, cssFile);


		if (settlementWindow == null) {
			settlementWindow = (SettlementWindow)(desktop.getToolWindow(SettlementWindow.NAME));
			if (settlementWindow != null) {
				settlementWindow.setTheme(txtColor);
				settlementWindow.setStatusBarTheme(cssFile);
			}
		}

		else {
			settlementWindow.setTheme(txtColor);
			settlementWindow.setStatusBarTheme(cssFile);
		}

		if (theme == 7) {
			speedIcon.setFill(Color.YELLOW);
			speedBtn.setGraphic(speedIcon);
			marsNetIcon.setFill(Color.YELLOW);
			marsNetBtn.setGraphic(marsNetIcon);
			soundIcon.setFill(Color.YELLOW);
			soundBtn.setGraphic(soundIcon);
			jfxTabPane.getStylesheets().add(getClass().getResource("/fxui/css/jfx_orange.css").toExternalForm());
		}

		else {
			speedIcon.setFill(Color.LAVENDER);
			speedBtn.setGraphic(speedIcon);
			marsNetIcon.setFill(Color.LAVENDER);
			marsNetBtn.setGraphic(marsNetIcon);
			soundIcon.setFill(Color.LAVENDER);
			soundBtn.setGraphic(soundIcon);
			jfxTabPane.getStylesheets().add(getClass().getResource("/fxui/css/jfx_blue.css").toExternalForm());
		}

		chatBox.update();

	}


	public void setStylesheet(JFXSlider s, String cssFile) {
		s.getStylesheets().clear();
		s.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
	}

	public void setStylesheet(Button b, String cssFile) {
		b.getStylesheets().clear();
		b.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
	}

	public void setStylesheet(JFXButton b, String cssFile) {
		b.getStylesheets().clear();
		b.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
	}

	public void setStylesheet(StackPane sp, String cssFile) {
		sp.getStylesheets().clear();
		sp.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
	}

	public void setStylesheet(VBox vb, String cssFile) {
		vb.getStylesheets().clear();
		vb.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
	}

	public void setStylesheet(JFXToggleButton b, String cssFile) {
		b.getStylesheets().clear();
		b.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
	}

	public void setStylesheet(Label l, String cssFile) {
		l.getStylesheets().clear();
		l.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
	}

	/**
	 * Creates and starts the earth timer
	 */
	public void startEarthTimer() {
		// Set up earth time text update
		timeline = new Timeline(new KeyFrame(Duration.millis(TIME_DELAY), ae -> updateTimeLabels()));
		// Note: Infinite Timeline might result in a memory leak if not stopped properly.
		// All the objects with animated properties would not be garbage collected.
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.play();

	}


    /**
     * Creates and returns a {@link Flyout}
     * @return  a new {@link Flyout}
     */
    //2015-11-11 Added createFlyout()
    public JFXPopup createFlyout() {
     	marsNetBtn = new JFXButton();
		marsNetIcon = new IconNode(FontAwesome.COMMENTING_O);
		marsNetIcon.setIconSize(20);
		//marsNetIcon.setStroke(Color.WHITE);

        //marsNetButton.setId("marsNetButton");
        //marsNetButton.setPadding(new Insets(0, 0, 0, 0)); // Warning : this significantly reduce the size of the button image
		setQuickToolTip(marsNetBtn, "Open MarsNet Chat Box");

		marsNetBox = new JFXPopup(createChatBox());
		//rootAnchorPane.getChildren().add(marsNetBox);
		marsNetBox.setOpacity(.9);
		//marsNetBox.setPopupContainer(rootAnchorPane);
		//marsNetBox.setSource(marsNetBtn);

		//chatBox.update();
        marsNetBtn.setOnAction(e -> {
            if (!flag)
            	chatBox.update();

            if (marsNetBox.isShowing()) {//.isVisible()) {
                marsNetBox.hide();//.close();
            }
            else {
            	openChatBox();
            }

        });

        return marsNetBox;
    }

    public void openChatBox() {
		try {
			TimeUnit.MILLISECONDS.sleep(200L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        chatBox.getAutoFillTextBox().getTextbox().clear();
        chatBox.getAutoFillTextBox().getTextbox().requestFocus();
    	marsNetBox.show(marsNetBtn, PopupVPosition.TOP, PopupHPosition.RIGHT, -15, 35);
    }


    //public void ToggleMarsNetButton(boolean value) {
    //	marsNetButton.setSelected(value);
    //}

    //public boolean isToggleMarsNetButtonSelected() {
    //	return marsNetButton.isSelected();
    //}

    //public void fireMarsNetButton() {
    //	marsNetButton.fire();
    //}

    public JFXPopup getFlyout() {
    	return marsNetBox;
    }

    /*
     * Creates a chat box
     * @return StackPane
     */
    //2015-11-11 Added createChatBox()
  	public StackPane createChatBox() {
  		chatBox = new ChatBox(this);
        chatBox.getAutoFillTextBox().getTextbox().requestFocus();
  		chatBoxPane = new StackPane(chatBox);
  		chatBoxPane.setMinHeight(chatBoxHeight);
 		chatBoxPane.setPrefHeight(chatBoxHeight);
 		chatBoxPane.setMaxHeight(chatBoxHeight);
  		chatBoxPane.setPadding(new Insets(0, 0, 0, 0));
  		return chatBoxPane;
  	}


  	public StackPane getChatBoxPane() {
  		return chatBoxPane;
  	}

  	public ChatBox getChatBox() {
  		return chatBox;
  	}

  	public void setChatBoxPaneHeight(double value) {
  		chatBoxPane.setMinHeight(value);
 		chatBoxPane.setPrefHeight(value);
 		chatBoxPane.setMaxHeight(value);
  	}

	/*
	 * Creates the time bar for MainScene
	 */
	public void createLastSaveBar() {
		//lastSaveBar = new HBox();
		//lastSaveBar.setPadding(new Insets(5,5,5,5));

		//2016-09-15 Added oldLastSaveStamp
		oldLastSaveStamp = sim.getLastSave();
		oldLastSaveStamp = oldLastSaveStamp.replace("_", " ");

		lastSaveLabel = new Label();
		lastSaveLabel.setId("save-label");
		lastSaveLabel.setMaxWidth(Double.MAX_VALUE);
		//lastSaveLabel.setMinWidth(220);
        if (OS.contains("linux")) {
            lastSaveLabel.setMinWidth(LINUX_WIDTH);
            lastSaveLabel.setPrefSize(LINUX_WIDTH, 29);
        }
        else if (OS.contains("mac")) {
            lastSaveLabel.setMinWidth(MACOS_WIDTH);
            lastSaveLabel.setPrefSize(MACOS_WIDTH, 28);
        }
        else {
            lastSaveLabel.setMinWidth(WIN_WIDTH);
            lastSaveLabel.setPrefSize(WIN_WIDTH, 33);
        }
		//lastSaveLabel.setPrefSize(220, 20);
        lastSaveLabel.setAlignment(Pos.CENTER_LEFT);
		lastSaveLabel.setTextAlignment(TextAlignment.LEFT);
		lastSaveLabel.setText(LAST_SAVED + oldLastSaveStamp);

		setQuickToolTip(lastSaveLabel, "Last time when the sim was (auto)saved");

		//lastSaveBar.getChildren().add(lastSaveLabel);
/*
		memMax = (int) Math.round(Runtime.getRuntime().maxMemory()) / 1000000;
		memFree = (int) Math.round(Runtime.getRuntime().freeMemory()) / 1000000;
		memTotal = (int) Math.round(Runtime.getRuntime().totalMemory()) / 1000000;
		memUsed = memTotal - memFree;
*/

	}


	/*
	 * Creates the status bar for MainScene

	public StatusBar createStatusBar() {
		if (statusBar == null) {
			statusBar = new StatusBar();
			statusBar.setText("");
		}

		//2016-09-15 Added oldLastSaveStamp
		oldLastSaveStamp = sim.instance().getLastSave();
		oldLastSaveStamp = oldLastSaveStamp.replace("_", " ");

		lastSaveText = new Label();
		lastSaveText.setText("Last Saved : " + oldLastSaveStamp + " ");
		lastSaveText.setStyle("-fx-text-inner-color: orange;");
		lastSaveText.setTooltip(new Tooltip ("Time last saved/autosaved on your machine"));

		statusBar.getLeftItems().add(new Separator(VERTICAL));
		statusBar.getLeftItems().add(lastSaveText);
		statusBar.getLeftItems().add(new Separator(VERTICAL));

		memMax = (int) Math.round(Runtime.getRuntime().maxMemory()) / 1000000;
		memFree = (int) Math.round(Runtime.getRuntime().freeMemory()) / 1000000;
		memTotal = (int) Math.round(Runtime.getRuntime().totalMemory()) / 1000000;
		memUsed = memTotal - memFree;

		statusBar.getRightItems().add(new Separator(VERTICAL));

		if (masterClock == null) {
			masterClock = Simulation.instance().getMasterClock();
		}

		if (earthClock == null) {
			earthClock = masterClock.getEarthClock();
		}

		timeText = new Label();
		timeText.setText("  " + timeStamp + "  ");
		timeText.setStyle("-fx-text-inner-color: orange;");
		timeText.setTooltip(new Tooltip ("Earth Date/Time"));

		statusBar.getRightItems().add(timeText);
		statusBar.getRightItems().add(new Separator(VERTICAL));

		return statusBar;
	}


	public NotificationPane getNotificationPane() {
		return notificationPane;
	}

	public Node createNotificationPane() {
		// wrap the dndTabPane inside notificationNode
		notificationPane = new NotificationPane(mainAnchorPane);

		String imagePath = getClass().getResource("/notification/notification-pane-warning.png").toExternalForm();
		ImageView image = new ImageView(imagePath);
		notificationPane.setGraphic(image);
		notificationPane.getActions().addAll(new Action("Close", ae -> {
			// do sync, then hide...
			notificationPane.hide();
		} ));

		notificationPane.setShowFromTop(false);
		notificationPane.setText("Breaking news for mars-simmers !!");
		return notificationPane;
	}


	public String getSampleName() {
		return "Notification Pane";
	}

	public String getControlStylesheetURL() {
		return "/org/controlsfx/control/notificationpane.css";
	}
*/

	/*
	 * Updates Earth and Mars time label in the earthTimeBar and marsTimeBar
	 */
	public void updateTimeLabels() {

		calendarDisplay.update();

		TPSLabel.setText(formatter.format(masterClock.getPulsesPerSecond()));

		upTimeLabel.setText(uptimer.getUptime());

		int solElapsed = marsClock.getSolElapsedFromStart();
		if (solElapsed != solElapsedCache) {

			if (solElapsed == 1) {
				String mn = marsClock.getMonthName();
				if (mn != null) {
					monthLabel.setText("Month : " + mn);
					if (mn.equals("Adir")) {
						yearLabel.setText("Year : " + marsClock.getOrbitString());
					}
				}
			}

			solElapsedCache = solElapsed;
		}

		StringBuilder m = new StringBuilder();
        m.append(MARS_DATE_TIME).append(marsClock.getDateString())//.append(ONE_SPACE)
        	.append(marsClock.getTrucatedTimeStringUMST());
		marsTimeButton.setText(m.toString());

		StringBuilder e = new StringBuilder();
        e.append(EARTH_DATE_TIME).append(earthClock.getTimeStamp2());
		earthTimeButton.setText(e.toString());

		//2016-09-15 Added oldLastSaveStamp and newLastSaveStamp
		if (sim.getJustSaved()) {
			String newLastSaveStamp = sim.getLastSave();
			if (!oldLastSaveStamp.equals(newLastSaveStamp)) {
				sim.setJustSaved(false);
				oldLastSaveStamp = newLastSaveStamp.replace("_", " ");
				lastSaveLabel.setText(LAST_SAVED + oldLastSaveStamp);
				//System.out.print("updated last save time stamp");
			}
		}
	}

	/**
	 * Gets the main desktop panel.
	 * @return desktop
	 */
	public MainDesktopPane getDesktop() {
		return desktop;
	}

	public boolean isMainSceneDone() {
		return isMainSceneDoneLoading;
	}

	/**
	 * Create a new simulation.
	 */
	public void newSimulation() {
		//logger.info("MainScene's newSimulation() is on " + Thread.currentThread().getName() + " Thread");

		if ((newSimThread == null) || !newSimThread.isAlive()) {
			newSimThread = new Thread(Msg.getString("MainWindow.thread.newSim")) { //$NON-NLS-1$
				@Override
				public void run() {
					Platform.runLater(() -> {
						newSimulationProcess();
					});
				}
			};
			newSimThread.start();
		} else {
			newSimThread.interrupt();
		}

	}

	/**
	 * Performs the process of creating a new simulation.
	 */
	private void newSimulationProcess() {
		//logger.info("MainScene's newSimulationProcess() is on " + Thread.currentThread().getName() + " Thread");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Starting new sim");
		alert.setHeaderText(Msg.getString("MainScene.new.header"));
		alert.setContentText(Msg.getString("MainScene.new.content"));
		ButtonType buttonTypeOne = new ButtonType("Save on Exit");
		//ButtonType buttonTypeTwo = new ButtonType("End Sim");
		ButtonType buttonTypeCancel = new ButtonType("Back to Sim");//, ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeOne,
				//buttonTypeTwo,
				buttonTypeCancel);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == buttonTypeOne) {
			saveOnExit();
		//} else if (result.get() == buttonTypeTwo) {
		//	endSim();
		} else if (result.get() == buttonTypeCancel) {//!result.isPresent())
			return;
		}
	}

	/**
	 * Save the current simulation. This displays a FileChooser to select the
	 * location to save the simulation if the default is not to be used.
	 *
	 * @param useDefault
	 *            Should the user be allowed to override location?
	 */
	public void saveSimulation(int type) {
		//logger.info("MainScene's saveSimulation() is on " + Thread.currentThread().getName() + " Thread");
		boolean previous = startPause();

		hideWaitStage(PAUSED);
		showWaitStage(SAVING);

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                saveSimulationProcess(type);
        		while (masterClock.isSavingSimulation())
        			TimeUnit.MILLISECONDS.sleep(200L);
                return null;
            }
            @Override
            protected void succeeded(){
                super.succeeded();
                hideWaitStage(SAVING);
            }
        };
        new Thread(task).start();

		endPause(previous);

	}

	/**
	 * Performs the process of saving a simulation.
	 */
	// 2015-01-08 Added autosave
	private void saveSimulationProcess(int type) {
		//logger.info("MainScene's saveSimulationProcess() is on " + Thread.currentThread().getName() + " Thread");
		fileLocn = null;
		dir = null;
		title = null;

		hideWaitStage(PAUSED);

		// 2015-01-25 Added autosave
		if (type == Simulation.AUTOSAVE) {
			dir = Simulation.AUTOSAVE_DIR;
			masterClock.saveSimulation(Simulation.AUTOSAVE, null);

		} else if (type == Simulation.SAVE_DEFAULT) {
			dir = Simulation.DEFAULT_DIR;
			masterClock.saveSimulation(Simulation.SAVE_DEFAULT, null);

		} else if (type == Simulation.SAVE_AS) {
			masterClock.setPaused(true);
			Platform.runLater(() -> {
				FileChooser chooser = new FileChooser();
				dir = Simulation.DEFAULT_DIR;
				File userDirectory = new File(dir);
				title = Msg.getString("MainScene.dialogSaveSim");
				chooser.setTitle(title); // $NON-NLS-1$
				chooser.setInitialDirectory(userDirectory);
				// Set extension filter
				FileChooser.ExtensionFilter simFilter = new FileChooser.ExtensionFilter("Simulation files (*.sim)",
						"*.sim");
				FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("all files (*.*)", "*.*");
				chooser.getExtensionFilters().addAll(simFilter, allFilter);
				File selectedFile = chooser.showSaveDialog(stage);
				if (selectedFile != null)
					fileLocn = selectedFile;// + Simulation.DEFAULT_EXTENSION;
				else
					return;

				hideWaitStage(PAUSED);
				showWaitStage(SAVING);

		        Task task = new Task<Void>() {
		            @Override
		            protected Void call() throws Exception {
		        		try {
		    				masterClock.saveSimulation(Simulation.SAVE_AS, fileLocn);

		        			while (masterClock.isSavingSimulation())
		        				TimeUnit.MILLISECONDS.sleep(200L);

		        		} catch (Exception e) {
		        			logger.log(Level.SEVERE, Msg.getString("MainWindow.log.saveError") + e); //$NON-NLS-1$
		        			e.printStackTrace(System.err);
		        		}

		                return null;
		            }
		            @Override
		            protected void succeeded(){
		                super.succeeded();
		                hideWaitStage(SAVING);
		            }
		        };
		        new Thread(task).start();
			});
		}
	}


	public void startPausePopup() {
		//System.out.println("calling startPausePopup(): messagePopup.numPopups() is " + messagePopup.numPopups());
		//if (messagePopup.numPopups() < 1) {
            // Note: (NOT WORKING) popups.size() is always zero no matter what.
		//	Platform.runLater(() ->
		//		messagePopup.popAMessage(PAUSE, ESC_TO_RESUME, " ", stage, Pos.TOP_CENTER, PNotification.PAUSE_ICON)
		//	);
		//}
	}

	public void stopPausePopup() {
		//Platform.runLater(() ->
		//	messagePopup.stop()
		//);

	}

	/**
	 * Pauses the marquee timer and pauses the simulation.
	 */
	public void pauseSimulation() {
		isMuteCache = desktop.getSoundPlayer().isMute(false);
		if (!isMuteCache)
			desktop.getSoundPlayer().setMute(true);
		desktop.getMarqueeTicker().pauseMarqueeTimer(true);
		masterClock.setPaused(true);
	}

	/**
	 * Unpauses the marquee timer and unpauses the simulation.
	 */
	public void unpauseSimulation() {
		masterClock.setPaused(false);
		desktop.getMarqueeTicker().pauseMarqueeTimer(false);
		if (!isMuteCache)
			desktop.getSoundPlayer().setMute(false);
	}

	public boolean startPause() {
		boolean previous = masterClock.isPaused();
		if (!previous) {
			pauseSimulation();
		}
		desktop.getTimeWindow().enablePauseButton(false);
		return previous;
	}

	public void endPause(boolean previous) {
		boolean now = masterClock.isPaused();
		if (!previous) {
			if (now) {
				unpauseSimulation();
 			}
		} else {
			if (!now) {
				unpauseSimulation();
  			}
		}
		desktop.getTimeWindow().enablePauseButton(true);
	}


	/**
	 * Ends the current simulation, closes the JavaFX stage of MainScene but leaves the main menu running
	 */
	private void endSim() {
		//logger.info("MainScene's endSim() is on " + Thread.currentThread().getName() + " Thread");
		Simulation.instance().endSimulation();
		Simulation.instance().getSimExecutor().shutdownNow();
		mainSceneExecutor.shutdownNow();
		getDesktop().clearDesktop();
		stage.close();
	}

	/**
	 * Exits the current simulation and the main menu.
	 */
	public void exitSimulation() {
		//logger.info("MainScene's exitSimulation() is on " + Thread.currentThread().getName() + " Thread");
		logger.info("Exiting the simulation. Bye!");
		// Save the UI configuration.
		UIConfig.INSTANCE.saveFile(this);
		masterClock.exitProgram();

	}


	public MainSceneMenu getMainSceneMenu() {
		return menuBar;
	}


	public Stage getStage() {
		return stage;
	}

	private void createSwingNode() {
		//createDesktops();
		desktop = new MainDesktopPane(this);
		SwingUtilities.invokeLater(() -> {
			swingNode.setContent(desktop);
		});
	}

	public SwingNode getSwingNode() {
		return swingNode;
	}

	/**
	 * Creates an Alert Dialog to confirm ending or exiting the simulation or
	 * MSP
	 */
	public boolean alertOnExit() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Leaving the sim");
		alert.initOwner(stage);
		alert.setHeaderText(Msg.getString("MainScene.exit.header"));
		alert.setContentText(Msg.getString("MainScene.exit.content"));
		ButtonType buttonTypeOne = new ButtonType("Save & Exit");
		//ButtonType buttonTypeTwo = new ButtonType("End Sim");
		ButtonType buttonTypeThree = new ButtonType("Exit Sim");
		ButtonType buttonTypeCancel = new ButtonType("Back to Sim", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeOne,
				//buttonTypeTwo,
				buttonTypeThree, buttonTypeCancel);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == buttonTypeOne) {
			saveOnExit();
			return true;

		} else if (result.get() == buttonTypeThree) {
			endSim();
			exitSimulation();
			Platform.exit();
			System.exit(0);
			return true;

		} else {
			return false;
		}
	}

	/**
	 * Initiates the process of saving a simulation.
	 */
	public void saveOnExit() {
		//logger.info("MainScene's saveOnExit() is on " + Thread.currentThread().getName() + " Thread");
		showWaitStage(SAVING);
		desktop.getTimeWindow().enablePauseButton(false);
		// Save the simulation as default.sim
		masterClock.saveSimulation(Simulation.SAVE_DEFAULT, null);

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
        		try {
        			masterClock.saveSimulation(Simulation.SAVE_DEFAULT, null);

        			while (masterClock.isSavingSimulation())
        				TimeUnit.MILLISECONDS.sleep(200L);

        		} catch (Exception e) {
        			logger.log(Level.SEVERE, Msg.getString("MainWindow.log.saveError") + e); //$NON-NLS-1$
        			e.printStackTrace(System.err);
        		}
                return null;
            }
            @Override
            protected void succeeded(){
                super.succeeded();
                hideWaitStage(SAVING);
                endSim();
                exitSimulation();
    			Platform.exit();
    			System.exit(0);
            }
        };
        new Thread(task).start();

	}

	public void openInitialWindows() {
		//logger.info("MainScene's openInitialWindows() is on " + Thread.currentThread().getName() + " Thread");
		//String OS = System.getProperty("os.name").toLowerCase();
		//System.out.println("OS is " + OS);
		if (OS.contains("mac")) {
		// SwingUtilities needed below for MacOSX
			SwingUtilities.invokeLater(() -> {
				desktop.openInitialWindows();
			});
		}
		else {
			desktop.openInitialWindows();
		}

		quote = new QuotationPopup(this);
		popAQuote();

		isMainSceneDoneLoading = true;

	}

	public void popAQuote() {
		quote.popAQuote(stage);
	}

	public MarsNode getMarsNode() {
		return marsNode;
	}

	public static int getTheme() {
		return theme;
	}

	public double getWidth() {
		return sceneWidth.get();
	}

	public double getHeight() {
		return sceneHeight.get();
	}

	public AnchorPane getAnchorPane() {
		return rootAnchorPane;
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

    private MenuItem registerAction(MenuItem menuItem) {
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //showPopup(borderPane, "You clicked the " + menuItem.getText() + " icon");
            	System.out.println("You clicked the " + menuItem.getText() + " icon");
            }
        });
        return menuItem;
    }


    /*
     * Create the progress circle animation while waiting for loading the main scene
     */
 	public void createProgressCircle(int type) {

 		if (type == LOADING) {
 	 		ProgressIndicator indicator = new ProgressIndicator();
 	 		indicator.setSkin(null);
 	 		//indicator.setOpacity(.5);
 	 		indicator.setStyle("-fx-background-color: transparent; ");
 	 		StackPane stackPane = new StackPane();
 	 		//stackPane.setOpacity(0.5);
 	 		stackPane.getChildren().add(indicator);
 	 		StackPane.setAlignment(indicator, Pos.CENTER);
 	 		stackPane.setBackground(Background.EMPTY);
 	 		stackPane.setStyle(
 	 	    		   //"-fx-border-style: none; "
 	 	       			"-fx-background-color: transparent; "
 	 	       			//+ "-fx-background-radius: 3px;"
 	 	    		   );
 	 		Scene scene = new Scene(stackPane, 100, 100);
 	 		scene.setFill(Color.TRANSPARENT);
 			loadingCircleStage = new Stage();
 			//loadingCircleStage.setOpacity(1);
 			setEscapeEventHandler(true, loadingCircleStage);
 			loadingCircleStage.initOwner(stage);
 			loadingCircleStage.initModality(Modality.WINDOW_MODAL); // Modality.NONE is by default if initModality() is NOT specified.
 			loadingCircleStage.getIcons().add(new Image(this.getClass().getResource("/icons/lander_hab64.png").toExternalForm()));
 			loadingCircleStage.initStyle (StageStyle.TRANSPARENT);
 			loadingCircleStage.setScene(scene);
 			loadingCircleStage.hide();
	 	}

 		else if (type == SAVING) {
 	 		//ProgressIndicator indicator = new ProgressIndicator();
 	 		MaskerPane indicator = new MaskerPane();
 	 		indicator.setSkin(null);
 	 		//indicator.setOpacity(.5);
 	 		indicator.setStyle("-fx-background-color: transparent; ");
 	 		//indicator.setScaleX(1.5);
 	 		//indicator.setScaleY(1.5);
 	 		StackPane stackPane = new StackPane();
 	 		//stackPane.setOpacity(0.5);
 	 		stackPane.getChildren().add(indicator);
 	 		StackPane.setAlignment(indicator, Pos.CENTER);
 	 		stackPane.setBackground(Background.EMPTY);
 	 		//stackPane.setStyle("-fx-background-color: transparent; ");
 	 		stackPane.setStyle(
 	 	    		   //"-fx-border-style: none; "
 	 	       			"-fx-background-color: transparent; "
 	 	       			//+ "-fx-background-radius: 3px;"
 	 	    		   );

 	 		Scene scene = new Scene(stackPane);//, 150, 150);
 	 		scene.setFill(Color.TRANSPARENT);
 	 		indicator.setText("Saving");
 			savingCircleStage = new Stage();
 			savingCircleStage.initOwner(stage);
 			savingCircleStage.initModality(Modality.WINDOW_MODAL); // Modality.NONE is by default if initModality() is NOT specified.
 			savingCircleStage.getIcons().add(new Image(this.getClass().getResource("/icons/lander_hab64.png").toExternalForm()));
 			savingCircleStage.initStyle (StageStyle.TRANSPARENT);
 			savingCircleStage.setScene(scene);
 			savingCircleStage.hide();
	 	}

 		else if (type == PAUSED) {
 			//messagePopup = new MessagePopup();
	 	}

		else
			System.out.println("MainScene's createProgressCircle() : type is invalid");

 	}


 	public void showWaitStage(int type) {
 		mainSceneExecutor.execute(new LoadWaitStageTask(type));
 	}

    /*
     * Set up a wait stage
     * @param type
     */
 	class LoadWaitStageTask implements Runnable {
 		int type;

 		LoadWaitStageTask(int type){
 			this.type = type;
 		}

 		public void run() {
 			//logger.info("LoadWaitStageTask is on " + Thread.currentThread().getName());
			Platform.runLater(() -> {
				//FXUtilities.runAndWait(() -> {}) does NOT work
				if (type == LOADING) {
					setMonitor(loadingCircleStage);
					loadingCircleStage.show();
				}
				else if (type == SAVING) {
					stopPausePopup();
					setMonitor(savingCircleStage);
					savingCircleStage.show();
				}
				else if (type == PAUSED) {
					stopPausePopup();
					startPausePopup();
				}
			});
 		}
 	}

 	public void hideWaitStage(int type) {
		//FXUtilities.runAndWait(() -> { // not working for loading sim
		if (type == LOADING)
			loadingCircleStage.hide();
		else if (type == SAVING)
			savingCircleStage.hide();
		else if (type == PAUSED) {
			stopPausePopup();
		}
		else
			System.out.println("MainScene's hideWaitStage() : type is invalid");

 	}

 	// 2016-06-27 Added setMonitor()
	public void setMonitor(Stage stage) {
		// Issue: how do we tweak mars-sim to run on the "active" monitor as chosen by user ?
		// "active monitor is defined by whichever computer screen the mouse pointer is or where the command console that starts mars-sim.
		// by default MSP runs on the primary monitor (aka monitor 0 as reported by windows os) only.
		// see http://stackoverflow.com/questions/25714573/open-javafx-application-on-active-screen-or-monitor-in-multi-screen-setup/25714762#25714762
		if (rootAnchorPane == null) {
			StackPane pane = new StackPane();//starfield);
			pane.setPrefHeight(sceneWidth.get());
			pane.setPrefWidth(sceneHeight.get());

			StartUpLocation startUpLoc = new StartUpLocation(pane.getPrefWidth(), pane.getPrefHeight());
		}
		else {
			StartUpLocation startUpLoc = new StartUpLocation(scene.getWidth(), scene.getHeight());
	        double xPos = startUpLoc.getXPos();
	        double yPos = startUpLoc.getYPos();
	        // Set Only if X and Y are not zero and were computed correctly
	        if (xPos != 0 && yPos != 0) {
	            stage.setX(xPos);
	            stage.setY(yPos);
	        }

            stage.centerOnScreen();
		}
	}



	// 2016-10-01 Added mainSceneExecutor for executing wait stages
    public void startMainSceneExecutor() {
        //logger.info("Simulation's startSimExecutor() is on " + Thread.currentThread().getName() + " Thread");
    	// INFO: Simulation's startSimExecutor() is on JavaFX-Launcher Thread
    	mainSceneExecutor = Executors.newSingleThreadExecutor();
    }


    public JFXTabPane getJFXTabPane() {
    	return jfxTabPane;
    }

    public Pane getRoot() {
    	return root;
    }

    public Scene getScene() {
    	return scene;
    }

    public JFXSlider getZoom() {
    	return zoomSlider;
    }

	public JFXComboBox<Settlement> getSBox() {
		return sBox;
	};

	public Settlement getSettlement() {
		return sBox.getSelectionModel().getSelectedItem();
	}

	public void setSettlement(Settlement s) {
		Platform.runLater(() -> {
			//if (!desktop.isToolWindowOpen(SettlementWindow.NAME))
			openSettlementMap();
			sBox.getSelectionModel().select(s);
		});
	}

	//public CheckComboBox<String> getMapLabelBox() {
	//	return mapLabelBox;
	//}
/*
	public void sendSnackBar(String msg) {
		snackbar.fireEvent(new SnackbarEvent(msg, "UNDO",3000,(b)->{}));
	}
*/
	/**
	 * Sets up the JavaFX's tooltip
	 * @param node
	 * @param tooltip's hint text
	 */
	public void setQuickToolTip(Node n, String s) {
		Tooltip tt = new Tooltip(s);
		tt.getStyleClass().add("ttip");

		n.setOnMouseEntered(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent event) {
		        Point2D p = n.localToScreen(n.getLayoutBounds().getMaxX(), n.getLayoutBounds().getMaxY()); //I position the tooltip at bottom right of the node (see below for explanation)
		        tt.show(n, p.getX(), p.getY());
		    }
		});
		n.setOnMouseExited(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent event) {
		        tt.hide();
		    }
		});

	}

	public double convertSlider2Volume(double y) {
		return .05*y + .5;
	}

	public double convertVolume2Slider(double x) {
		return 20D*(x - .5);
	}

	public double getInitialTimeRatio() {
		return initial_time_ratio;
	}

	public JFXButton getMarsNetBtn() {
		return marsNetBtn;
	}

	public static void disableSound() {
		soundPlayer.enableMasterGain(false);
		if (soundSlider != null) soundSlider.setDisable(true);//.setValue(0);
	}

	public void destroy() {
		quote = null;
		messagePopup = null;
		topFlapBar = null;
		marsNetBox = null;
		marsNetBtn = null;
		chatBox = null;
		mainAnchorPane = null;
		rootAnchorPane = null;
		newSimThread = null;
		stage = null;
		loadingCircleStage = null;
		savingCircleStage = null;
		pausingCircleStage = null;
		timeline = null;
		notificationPane = null;
		desktop.destroy();
		desktop = null;
		menuBar = null;
		marsNode = null;
		transportWizard = null;
		constructionWizard = null;
	}
}
