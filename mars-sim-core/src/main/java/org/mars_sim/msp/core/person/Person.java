/**
 * Mars Simulation Project
 * Person.java
 * @version 3.1.0 2017-01-14
 * @author Scott Davis
 */

package org.mars_sim.msp.core.person;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mars_sim.msp.core.LifeSupportInterface;
import org.mars_sim.msp.core.LogConsolidated;
import org.mars_sim.msp.core.SimulationConfig;
import org.mars_sim.msp.core.Unit;
import org.mars_sim.msp.core.UnitEventType;
import org.mars_sim.msp.core.location.LocationSituation;
import org.mars_sim.msp.core.location.LocationStateType;
import org.mars_sim.msp.core.mars.MarsSurface;
import org.mars_sim.msp.core.person.ai.Mind;
import org.mars_sim.msp.core.person.ai.NaturalAttributeManager;
import org.mars_sim.msp.core.person.ai.NaturalAttributeType;
import org.mars_sim.msp.core.person.ai.SkillManager;
import org.mars_sim.msp.core.person.ai.SkillType;
import org.mars_sim.msp.core.person.ai.job.Job;
import org.mars_sim.msp.core.person.ai.job.JobAssignmentType;
import org.mars_sim.msp.core.person.ai.job.JobHistory;
import org.mars_sim.msp.core.person.ai.job.JobUtil;
import org.mars_sim.msp.core.person.ai.job.Politician;
import org.mars_sim.msp.core.person.ai.mission.Mission;
import org.mars_sim.msp.core.person.ai.mission.MissionMember;
import org.mars_sim.msp.core.person.ai.role.Role;
import org.mars_sim.msp.core.person.ai.role.RoleType;
import org.mars_sim.msp.core.person.health.MedicalAid;
import org.mars_sim.msp.core.reportingAuthority.CNSAMissionControl;
import org.mars_sim.msp.core.reportingAuthority.CSAMissionControl;
import org.mars_sim.msp.core.reportingAuthority.ESAMissionControl;
import org.mars_sim.msp.core.reportingAuthority.ISROMissionControl;
import org.mars_sim.msp.core.reportingAuthority.JAXAMissionControl;
import org.mars_sim.msp.core.reportingAuthority.MarsSocietyMissionControl;
import org.mars_sim.msp.core.reportingAuthority.NASAMissionControl;
import org.mars_sim.msp.core.reportingAuthority.RKAMissionControl;
import org.mars_sim.msp.core.reportingAuthority.ReportingAuthority;
import org.mars_sim.msp.core.reportingAuthority.ReportingAuthorityType;
import org.mars_sim.msp.core.reportingAuthority.SpaceXMissionControl;
import org.mars_sim.msp.core.science.ScienceType;
import org.mars_sim.msp.core.structure.Settlement;
import org.mars_sim.msp.core.structure.building.Building;
import org.mars_sim.msp.core.structure.building.BuildingManager;
import org.mars_sim.msp.core.structure.building.function.FunctionType;
import org.mars_sim.msp.core.structure.building.function.LifeSupport;
import org.mars_sim.msp.core.structure.building.function.LivingAccommodations;
import org.mars_sim.msp.core.structure.building.function.cooking.Cooking;
import org.mars_sim.msp.core.structure.building.function.cooking.PreparingDessert;
import org.mars_sim.msp.core.time.EarthClock;
import org.mars_sim.msp.core.tool.RandomUtil;
import org.mars_sim.msp.core.vehicle.Crewable;
import org.mars_sim.msp.core.vehicle.Medical;
import org.mars_sim.msp.core.vehicle.Vehicle;
import org.mars_sim.msp.core.vehicle.VehicleOperator;

/**
 * The Person class represents a person on Mars. It keeps track of everything
 * related to that person and provides information about him/her.
 */
public class Person extends Unit implements VehicleOperator, MissionMember, Serializable {

	/** default serial id. */
	private static final long serialVersionUID = 1L;
	/* default logger. */
	private static transient Logger logger = Logger.getLogger(Person.class.getName());
	private static String loggerName = logger.getName();
	private static String sourceName = loggerName.substring(loggerName.lastIndexOf(".") + 1, loggerName.length());

	public static final int MAX_NUM_SOLS = 3;

	private final static String POLITICIAN = Politician.class.getSimpleName();
	private final static String EARTH = "Earth";
	private final static String MARS = "Mars";
	private final static String HEIGHT = "Height";
	private final static String WEIGHT = "Weight";

	/** The unit count for this person. */
	private static int uniqueCount = Unit.FIRST_PERSON_ID;
	/** The average height of a person. */
	private static double averageHeight;
	/** The average weight of a person. */
	private static double averageWeight;
	/** The average upper height of a person. */
	private static double tall;
	/** The average low height of a person. */
	private static double shortH;
	/** The average high weight of a person. */
	private static double highW;
	/** The average low weight of a person. */
	private static double lowW;

	// Data members
	/** True if the person is a preconfigured crew member. */
	private boolean preConfigured;
	/** True if the person is born on Mars. */
	private boolean bornOnMars;
	/** True if the person is buried. */
	private boolean isBuried;
	/** True if the person is declared dead. */
	private boolean declaredDead;

	/** Unique identifier for this person. */
	private int identifier;
	/** The year of birth of a person */
	private int year;
	/** The month of birth of a person */
	private int month;
	/** The day of birth of a person */
	private int day;
	/** The age of a person */
	private int age;
	/** The cache for sol. */
	private int solCache = 1;
	/** The settlement the person is currently associated with. */
	private int associatedSettlement = -1;
	/** The buried settlement if the person has been deceased. */
	private int buriedSettlement = -1;
	/** The vehicle the person is on. */
	private int vehicle = -1;

	/** The cache for msol1 */
	private double msolCache = -1D;
	/** The eating speed of the person [kg/millisol]. */
	private double eatingSpeed = Math.max(.075, .1 + .1 / 5D * RandomUtil.getGaussianDouble());
	/** The height of the person (in cm). */
	private double height;
	/** The height of the person (in kg). */
	private double weight;
	/** Settlement X location (meters) from settlement center. */
	private double xLoc;
	/** Settlement Y location (meters) from settlement center. */
	private double yLoc;
	/** The birth timestamp of the person. */
	private String birthTimeStamp;
	/** The birthplace of the person. */
	private String birthplace;
	/** The person's name. */
	private String name;
	/** The person's first name. */
	private String firstName;
	/** The person's last name. */
	private String lastName;
	/** The person's sponsor. */
	private String sponsor;
	/** The person's country of origin. */
	private String country;
	/** The person's blood type. */
	private String bloodType;

	/** The gender of the person (male or female). */
	private GenderType gender;
	/** The person's skill manager. */
	private SkillManager skillManager;
	/** Manager for Person's natural attributes. */
	private NaturalAttributeManager attributes;
	/** Person's mind. */
	private Mind mind;
	/** Person's physical condition. */
	private PhysicalCondition condition;
	/** Person's circadian clock. */
	private CircadianClock circadian;

	private Favorite favorite;

	private TaskSchedule taskSchedule;

	private JobHistory jobHistory;

	private Role role;

	private Preference preference;

	private LifeSupportInterface support;

	private Cooking kitchenWithMeal;

	private PreparingDessert kitchenWithDessert;

	private ReportingAuthority ra;

	private Point2D bed;

	private Building quarters;

	private Building currentBuilding;

	/** The person's achievement in scientific fields. */
	private Map<ScienceType, Double> scientificAchievement;
	/** The person's paternal chromosome. */
	private Map<Integer, Gene> paternal_chromosome;
	/** The person's maternal chromosome. */
	private Map<Integer, Gene> maternal_chromosome;
	/** The person's mission experiences */
	private Map<Integer, List<Double>> missionExperiences;
	/** The person's EVA times */
	private Map<Integer, Map<String, Double>> eVATaskTime;
	/** The person's water/oxygen consumption */
	private Map<Integer, Map<Integer, Double>> consumption;

	private static PersonConfig pc = SimulationConfig.instance().getPersonConfig();

	static {
		// Compute the average height for all
		tall = pc.getTallAverageHeight();
		shortH = pc.getShortAverageHeight();
		averageHeight = (tall + shortH) / 2D;
		// Compute the average weight for all
		highW = pc.getHighAverageWeight();
		lowW = pc.getLowAverageWeight();
		averageWeight = (highW + lowW) / 2D;
	}

	/**
	 * Must be synchronised to prevent duplicate ids being assigned via different
	 * threads.
	 * 
	 * @return
	 */
	private static synchronized int getNextIdentifier() {
		return uniqueCount++;
	}
	
	/**
	 * Get the unique identifier for this person
	 * 
	 * @return Identifier
	 */
	public int getIdentifier() {
		return identifier;
	}
	
	/**
	 * Constructor 1 : used by PersonBuilderImpl Creates a Person object at a given
	 * settlement.
	 *
	 * @param name       the person's name
	 * @param settlement {@link Settlement} the settlement the person is at
	 * @throws Exception if no inhabitable building available at settlement.
	 */
	protected Person(String name, Settlement settlement) {
		super(name, settlement.getCoordinates());
		super.setDescription(settlement.getName());
		// Gets the identifier
		this.identifier = getNextIdentifier();
		// Add the person to the lookup map
		unitManager.addPersonID(this);

//		// Place this person within a settlement
//		enter(LocationCodeType.SETTLEMENT);
//		// Place this person within a building
//		enter(LocationCodeType.BUILDING);

		// Initialize data members
		this.name = name;
		firstName = name.substring(0, name.indexOf(" "));
		lastName = name.substring(name.indexOf(" ") + 1, name.length());
		this.xLoc = 0D;
		this.yLoc = 0D;
		this.associatedSettlement = settlement.getIdentifier();
		
		// Construct the NaturalAttributeManager instance
		attributes = new NaturalAttributeManager(this);
		// Construct the SkillManager instance
		skillManager = new SkillManager(this);
		// Construct the Mind instance
		mind = new Mind(this);

		// Set up the time stamp for the person
		birthTimeStamp = createBirthTimeStamp();
		// Set the person's status of death
		isBuried = false;
	}

	/*
	 * Uses static factory method to create an instance of PersonBuilder
	 */
	public static PersonBuilder<?> create(String name, Settlement settlement) {
		return new PersonBuilderImpl(name, settlement);
	}

	/**
	 * Initialize field data, class and maps
	 */
	public void initialize() {
		// Add the person to the lookup map
		unitManager.addPersonID(this);
		// Put person in proper building.
		unitManager.getSettlementByID(associatedSettlement).getInventory().storeUnit(this);
		// WARNING: setAssociatedSettlement(settlement) will cause suffocation when
		// reloading from a saved sim
		BuildingManager.addToRandomBuilding(this, associatedSettlement);
		// why failed in
		// testWalkingStepsRoverToExterior(org.mars_sim.msp.core.person.ai.task.WalkingStepsTest)
		// Set up genetic make-up. Notes it requires attributes.
		setupChromosomeMap();

		jobHistory = new JobHistory(this);

		circadian = new CircadianClock(this);

		condition = new PhysicalCondition(this);

		scientificAchievement = new HashMap<ScienceType, Double>(0);

		favorite = new Favorite(this);

		role = new Role(this);

		taskSchedule = new TaskSchedule(this);

		mind.getTaskManager().initialize();

		preference = new Preference(this);

		support = getLifeSupportType();
		// Create the mission experiences map
		missionExperiences = new ConcurrentHashMap<>();
		// Create the EVA hours map
		eVATaskTime = new ConcurrentHashMap<>();
		// Create the consumption map
		consumption = new ConcurrentHashMap<>();
		// Asssume the person is not a preconfigured crew member
		preConfigured = false;
	}

	/**
	 * Initialize field data, class and maps
	 */
	public void initializeMock() {
		if (unitManager == null) {
			System.out.println("Person's initializeMock() : unitManager is null");
		}
		unitManager.getSettlementByID(associatedSettlement).getInventory().storeUnit(this);
		BuildingManager.addToRandomBuilding(this, associatedSettlement);
		isBuried = false;
		attributes = new NaturalAttributeManager(this);
//		mind = new Mind(this);
//		mind.getTaskManager().initialize();
	}

	/**
	 * Compute a person's chromosome map
	 */
	public void setupChromosomeMap() {
		paternal_chromosome = new HashMap<>();
		maternal_chromosome = new HashMap<>();

		if (bornOnMars) {
			// TODO: figure out how to account for growing characteristics such as height
			// and weight
			// and define various traits get passed on from parents
		} else {
			// Biochemistry: id 0 - 19
			setupBloodType();
			// Physical Characteristics: id 20 - 39
			// Set up Height
			setupHeight();
			// Set up Weight
			setupWeight();
			// Set up personality traits: id 40 - 59
			setupAttributeTrait();
		}

	}

	/**
	 * Compute a person's attributes and its chromosome
	 */
	public void setupAttributeTrait() {
		// TODO: set up a set of genes that was passed onto this person from two
		// hypothetical parents
		int ID = 40;
		boolean dominant = false;

		int strength = attributes.getAttribute(NaturalAttributeType.STRENGTH);
		// Set inventory total mass capacity based on the person's strength.
		getInventory().addGeneralCapacity(pc.getBaseCapacity() + strength);

		int rand = RandomUtil.getRandomInt(100);

		Gene trait1_G = new Gene(this, ID, "Trait 1", true, dominant, "Introvert", rand);
		paternal_chromosome.put(ID, trait1_G);

	}

	/**
	 * Compute a person's blood type and its chromosome
	 */
	public void setupBloodType() {
		int ID = 1;
		boolean dominant = false;

		String dad_bloodType = null;
		int rand = RandomUtil.getRandomInt(2);
		if (rand == 0) {
			dad_bloodType = "A";
			dominant = true;
		} else if (rand == 1) {
			dad_bloodType = "B";
			dominant = true;
		} else if (rand == 2) {
			dad_bloodType = "O";
			dominant = false;
		}

		// Biochemistry 0 - 19
		Gene dad_bloodType_G = new Gene(this, ID, "Blood Type", true, dominant, dad_bloodType, 0);
		paternal_chromosome.put(ID, dad_bloodType_G);

		String mom_bloodType = null;
		rand = RandomUtil.getRandomInt(2);
		if (rand == 0) {
			mom_bloodType = "A";
			dominant = true;
		} else if (rand == 1) {
			mom_bloodType = "B";
			dominant = true;
		} else if (rand == 2) {
			mom_bloodType = "O";
			dominant = false;
		}

		Gene mom_bloodType_G = new Gene(this, 0, "Blood Type", false, dominant, mom_bloodType, 0);
		maternal_chromosome.put(0, mom_bloodType_G);

		if (dad_bloodType.equals("A") && mom_bloodType.equals("A"))
			bloodType = "A";
		else if (dad_bloodType.equals("A") && mom_bloodType.equals("B"))
			bloodType = "AB";
		else if (dad_bloodType.equals("A") && mom_bloodType.equals("O"))
			bloodType = "A";
		else if (dad_bloodType.equals("B") && mom_bloodType.equals("A"))
			bloodType = "AB";
		else if (dad_bloodType.equals("B") && mom_bloodType.equals("B"))
			bloodType = "B";
		else if (dad_bloodType.equals("B") && mom_bloodType.equals("O"))
			bloodType = "B";
		else if (dad_bloodType.equals("O") && mom_bloodType.equals("A"))
			bloodType = "A";
		else if (dad_bloodType.equals("O") && mom_bloodType.equals("B"))
			bloodType = "B";
		else if (dad_bloodType.equals("O") && mom_bloodType.equals("O"))
			bloodType = "O";

	}

	/**
	 * Compute a person's height and its chromosome
	 */
	public void setupHeight() {
		int ID = 20;
		boolean dominant = false;

		// For a 20-year-old in the US:
		// male : height : 176.5 weight : 68.5
		// female : height : 162.6 weight : 57.2

		// TODO: factor in country of origin.
		// TODO: look for a gender-correlated curve

		// Note: p = mean + RandomUtil.getGaussianDouble() * standardDeviation
		// Attempt to compute height with gaussian curve

		double dad_height = tall + RandomUtil.getGaussianDouble() * tall / 7D;// RandomUtil.getRandomInt(22);
		double mom_height = shortH + RandomUtil.getGaussianDouble() * shortH / 10D;// RandomUtil.getRandomInt(15);

		Gene dad_height_G = new Gene(this, ID, HEIGHT, true, dominant, null, dad_height);
		paternal_chromosome.put(ID, dad_height_G);

		Gene mom_height_G = new Gene(this, ID, HEIGHT, false, dominant, null, mom_height);
		maternal_chromosome.put(ID, mom_height_G);

		double genetic_factor = .65;
		double sex_factor = (tall - averageHeight) / averageHeight;
		// Add arbitrary (US-based) sex and genetic factor
		if (gender == GenderType.MALE)
			height = Math.round(
					(genetic_factor * dad_height + (1 - genetic_factor) * mom_height * (1 + sex_factor)) * 100D) / 100D;
		else
			height = Math.round(
					((1 - genetic_factor) * dad_height + genetic_factor * mom_height * (1 - sex_factor)) * 100D) / 100D;

	}

	/**
	 * Compute a person's weight and its chromosome
	 */
	public void setupWeight() {
		int ID = 21;
		boolean dominant = false;

		// For a 20-year-old in the US:
		// male : height : 176.5 weight : 68.5
		// female : height : 162.6 weight : 57.2

		// TODO: factor in country of origin.
		// TODO: look for a gender-correlated curve

		// Note: p = mean + RandomUtil.getGaussianDouble() * standardDeviation
		// Attempt to compute height with gaussian curve
		double dad_weight = highW + RandomUtil.getGaussianDouble() * highW / 13.5;// RandomUtil.getRandomInt(10);
		double mom_weight = lowW + RandomUtil.getGaussianDouble() * lowW / 10.5;// RandomUtil.getRandomInt(15);

		Gene dad_weight_G = new Gene(this, ID, WEIGHT, true, dominant, null, dad_weight);
		paternal_chromosome.put(ID, dad_weight_G);

		Gene mom_weight_G = new Gene(this, ID, WEIGHT, false, dominant, null, mom_weight);
		maternal_chromosome.put(ID, mom_weight_G);

		double genetic_factor = .65;
		double sex_factor = (highW - averageWeight) / averageWeight; // for male
		double height_factor = height / averageHeight;

		// Add arbitrary (US-based) sex and genetic factor
		if (gender == GenderType.MALE)
			weight = Math.round(height_factor
					* (genetic_factor * dad_weight + (1 - genetic_factor) * mom_weight * (1 + sex_factor)) * 100D)
					/ 100D;
		else
			weight = Math.round(height_factor
					* ((1 - genetic_factor) * dad_weight + genetic_factor * mom_weight * (1 - sex_factor)) * 100D)
					/ 100D;

		setBaseMass(weight);

	}

	/*
	 * Sets sponsoring agency for the person
	 */
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;

		if (sponsor.contains(ReportingAuthorityType.CNSA.getName())) {
			ra = CNSAMissionControl.createMissionControl(); // ProspectingMineral

		} else if (sponsor.contains(ReportingAuthorityType.CSA.getName())) {
			ra = CSAMissionControl.createMissionControl(); // AdvancingSpaceKnowledge

		} else if (sponsor.contains(ReportingAuthorityType.ESA.getName())) {
			ra = ESAMissionControl.createMissionControl(); // DevelopingSpaceActivity;

		} else if (sponsor.contains(ReportingAuthorityType.ISRO.getName())) {
			ra = ISROMissionControl.createMissionControl(); // DevelopingAdvancedTechnology

		} else if (sponsor.contains(ReportingAuthorityType.JAXA.getName())) {
			ra = JAXAMissionControl.createMissionControl(); // ResearchingSpaceApplication

		} else if (sponsor.contains(ReportingAuthorityType.NASA.getName())) {
			ra = NASAMissionControl.createMissionControl(); // FindingLife

		} else if (sponsor.contains(ReportingAuthorityType.RKA.getName())) {
			ra = RKAMissionControl.createMissionControl(); // ResearchingHealthHazard

		} else if (sponsor.contains(ReportingAuthorityType.MS.getName())) {
			ra = MarsSocietyMissionControl.createMissionControl(); // SettlingMars

		} else if (sponsor.contains(ReportingAuthorityType.SPACEX.getName())) {
			ra = SpaceXMissionControl.createMissionControl(); // BuildingSelfSustainingColonies

		} else {
			logger.warning(name + " has no reporting authority!");

		}
	}

	/*
	 * Gets sponsoring agency for the person
	 */
	public ReportingAuthority getReportingAuthority() {
		return ra;
	}

	/*
	 * Gets task preference for the person
	 */
	public Preference getPreference() {
		return preference;
	}

	/**
	 * Sets the role for a person.
	 * 
	 * @param type {@link RoleType}
	 */
	public void setRole(RoleType type) {
		getRole().setNewRoleType(type);

		if (type == RoleType.MAYOR) {
			// Set the job as Politician
			Job job = JobUtil.getJob(POLITICIAN);
			if (job != null) {
				mind.setJob(job, true, JobUtil.SETTLEMENT, JobAssignmentType.APPROVED, JobUtil.SETTLEMENT);
			}
		}
	}

	/**
	 * Sets the job of a person
	 * 
	 * @param jobStr
	 * @param authority
	 */
	public void setJob(String jobStr, String authority) {
		Job job = JobUtil.getJob(jobStr);
		if (job != null) {
			mind.setJob(job, true, JobUtil.SETTLEMENT, JobAssignmentType.APPROVED, authority);
		}
	}

	/**
	 * Gets the instance of Role for a person.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Gets the instance of JobHistory for a person.
	 */
	public JobHistory getJobHistory() {
		return jobHistory;
	}

	/**
	 * Gets the instance of Favorite for a person.
	 */
	public Favorite getFavorite() {
		return favorite;
	}

	/**
	 * Gets the instance of the task schedule for a person.
	 */
	public TaskSchedule getTaskSchedule() {
		return taskSchedule;
	}

	/**
	 * Create a string representing the birth time of the person.
	 *
	 * @return birth time string.
	 */
	private String createBirthTimeStamp() {
		StringBuilder s = new StringBuilder();
		// Set a birth time for the person
		year = EarthClock.getCurrentYear(earthClock) - RandomUtil.getRandomInt(22, 62);
		// 2003 + RandomUtil.getRandomInt(10) + RandomUtil.getRandomInt(10);
		s.append(year);

		month = RandomUtil.getRandomInt(11) + 1;
		s.append("-");
		if (month < 10)
			s.append(0);
		s.append(month).append("-");

		if (month == 2) {
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				day = RandomUtil.getRandomInt(28) + 1;
			} else {
				day = RandomUtil.getRandomInt(27) + 1;
			}
		}

		else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			day = RandomUtil.getRandomInt(30) + 1;
		} else {
			day = RandomUtil.getRandomInt(29) + 1;
		}

		// TODO: find out why sometimes day = 0 as seen on
		if (day == 0) {
			logger.warning(name + "'s date of birth is on the day 0th. Incrementing to the 1st.");
			day = 1;
		}

		// Set the age
		age = updateAge();

		if (day < 10)
			s.append(0);
		s.append(day).append(" ");

		int hour = RandomUtil.getRandomInt(23);
		if (hour < 10)
			s.append(0);
		s.append(hour).append(":");

		int minute = RandomUtil.getRandomInt(59);
		if (minute < 10)
			s.append(0);
		s.append(minute).append(":");

		int second = RandomUtil.getRandomInt(59);
		if (second < 10)
			s.append(0);
		s.append(second).append(".000");

		return s.toString();
	}

	/**
	 * Is the person outside of a settlement but within its vicinity
	 * 
	 * @return true if the person is just right outside of a settlement
	 */
	public boolean isRightOutsideSettlement() {
		if (LocationStateType.OUTSIDE_SETTLEMENT_VICINITY == currentStateType || isBuried)
			return true;
		return false;
	}

//	/**
//	 * Is the person in a vehicle inside a garage
//	 * 
//	 * @return true if the person is in a vehicle inside a garage
//	 */
//	public boolean isInVehicleInGarage() {
//		if (getContainerUnit() instanceof Vehicle) {
//			Building b = BuildingManager.getBuilding((Vehicle) getContainerUnit());
//			if (b != null)
//				// still inside the garage
//				return true;
//		}
//		return false;
//	}

	/**
	 * Get a person's location situation
	 * 
	 * @return {@link LocationSituation} the person's location
	 */
	public LocationSituation getLocationSituation() {
		if (isBuried)
			return LocationSituation.BURIED;
		else {
			Unit container = getContainerUnit();
			if (container instanceof Settlement)
				return LocationSituation.IN_SETTLEMENT;
			else if (container instanceof Vehicle)
				return LocationSituation.IN_VEHICLE;
			else if (container instanceof MarsSurface)
				return LocationSituation.OUTSIDE;
			else
				return LocationSituation.UNKNOWN;
		}

	}

	/**
	 * Gets the person's X location at a settlement.
	 *
	 * @return X distance (meters) from the settlement's center.
	 */
	public double getXLocation() {
		return xLoc;
	}

	/**
	 * Sets the person's X location at a settlement.
	 *
	 * @param xLocation the X distance (meters) from the settlement's center.
	 */
	public void setXLocation(double xLocation) {
		this.xLoc = xLocation;
	}

	/**
	 * Gets the person's Y location at a settlement.
	 *
	 * @return Y distance (meters) from the settlement's center.
	 */
	public double getYLocation() {
		return yLoc;
	}

	/**
	 * Sets the person's Y location at a settlement.
	 *
	 * @param yLocation
	 */
	public void setYLocation(double yLocation) {
		this.yLoc = yLocation;
	}

	/**
	 * Get settlement person is at, null if person is not at a settlement
	 *
	 * @return the person's settlement
	 */
	public Settlement getSettlement() {
//		System.out.println("Person: getContainerID() is " + getContainerID());
		if (getContainerID() == 0)
			return null;
//		
//		else if (vehicle == 0)
//			return null;
//
//		else
//			return unitManager.getSettlementByID(getContainerID());

		// TODO: what if a person is in a EVASuit inside an airlock in a settlement ?

		Unit c = getContainerUnit();

		if (c instanceof Settlement) {
			return (Settlement) c;
		}

		else if (c instanceof Vehicle) {
			Building b = BuildingManager.getBuilding((Vehicle) c);
			if (b != null)
				// still inside the garage
				return b.getSettlement();
		}
		return null;
	}

	/**
	 * Sets the unit's container unit. Overridden from Unit class.
	 *
	 * @param containerUnit the unit to contain this unit.
	 */
	public void setContainerUnit(Unit containerUnit) {
		super.setContainerUnit(containerUnit);
		if (containerUnit instanceof Vehicle) {
			vehicle = containerUnit.getIdentifier();
		} else
			vehicle = -1;
	}

	/**
	 * Bury the Person at the current location. This happens only if the person can
	 * be retrieved from any containing Settlements or Vehicles found. The body is
	 * fixed at the last location of the containing unit.
	 */
	public void buryBody() {
		// Remove the person from the settlement
//		getContainerUnit().getInventory().retrieveUnit(this);
		// Bury the body
		isBuried = true;
		// Back up the last container unit
//		condition.getDeathDetails().backupContainerUnit(containerUnit);
		condition.getDeathDetails().backupContainerID(getContainerID());
		// set container unit to null if not done so
		setContainerUnit(null);
		// Set his/her currentStateType
		currentStateType = LocationStateType.OUTSIDE_SETTLEMENT_VICINITY;  
		// Set his/her buried settlement
		setBuriedSettlement(associatedSettlement);
		// Remove the person from being a member of the associated settlement
		setAssociatedSettlement(-1);
		// Throw unit event.
		fireUnitUpdate(UnitEventType.BURIAL_EVENT);
	}

	protected void setDescription(String s) {
		super.setDescription(s);
	}

	/**
	 * Declares the person dead and removes the designated quarter
	 */
	void setDeclaredDead() {

		declaredDead = true;
		// Set quarters to null
		if (quarters != null) {
			LivingAccommodations accommodations = quarters.getLivingAccommodations();
			accommodations.getBedMap().remove(this);
			quarters = null;
		}
		// Empty the bed
		if (bed != null)
			bed = null;

	}

	/**
	 * Person can take action with time passing
	 *
	 * @param time amount of time passing (in millisols).
	 */
	public void timePassing(double time) {

		double msol1 = marsClock.getMillisolOneDecimal();

		if (msolCache != msol1) {
			msolCache = msol1;

			// If Person is dead, then skip
			if (!condition.isDead() && getLifeSupportType() != null) {// health.getDeathDetails() == null) {

				support = getLifeSupportType();

				circadian.timePassing(time, support);
				// Pass the time in the physical condition first as this may result in death.
				condition.timePassing(time, support);

				if (!condition.isDead()) {

					try {
						// Mental changes with time passing.
						mind.timePassing(time);
					} catch (Exception ex) {
						ex.printStackTrace();
						LogConsolidated.log(Level.SEVERE, 2000, sourceName, "[" + getLocationTag().getLocale() + "] "
								+ getName() + "'s Mind was having trouble processing task selection.", ex);
					}

					// check for the passing of each day
					int solElapsed = marsClock.getMissionSol();

					if (solCache != solElapsed) {
						// Check if a person's age should be updated
						age = updateAge();
						solCache = solElapsed;

						// Limit the size of the dailyWaterUsage to x key value pairs
						if (consumption.size() > MAX_NUM_SOLS)
							consumption.remove(solElapsed - MAX_NUM_SOLS);

						if (solElapsed % 3 == 0) {
							// Adjust the shiftChoice once every 3 sols based on sleep hour
							int bestSleepTime[] = getPreferredSleepHours();
							taskSchedule.adjustShiftChoice(bestSleepTime);
						}

						if (solElapsed % 4 == 0) {
							// Increment the shiftChoice once every 4 sols
							taskSchedule.incrementShiftChoice();
						}

						if (solElapsed % 7 == 0) {
							// Normalize the shiftChoice once every week
							taskSchedule.normalizeShiftChoice();
						}
					}
				}
			}

			else if (!isBuried && condition.getDeathDetails() != null
					&& condition.getDeathDetails().getBodyRetrieved()) {

				if (!declaredDead) {
					setDeclaredDead();
					mind.setInactive();
				}
			}
		}

		// final long time1 = System.nanoTime();
		// logger.info((time1-time0)/1.0e3 + " ms to process " + name);
	}

	/**
	 * Returns a reference to the Person's natural attribute manager
	 *
	 * @return the person's natural attribute manager
	 */
	public NaturalAttributeManager getNaturalAttributeManager() {
		return attributes;
	}

	/**
	 * Get the performance factor that effect Person with health complaint.
	 *
	 * @return The value is between 0 -> 1.
	 */
	public double getPerformanceRating() {
		return condition.getPerformanceFactor();
	}

	/**
	 * Returns a reference to the Person's physical condition
	 *
	 * @return the person's physical condition
	 */
	public PhysicalCondition getPhysicalCondition() {
		return condition;
	}

	/**
	 * Find a medical aid according to the current location.
	 *
	 * @return Accessible aid.
	 */
	MedicalAid getAccessibleAid() {
		MedicalAid found = null;

		Settlement settlement = getSettlement();
		if (settlement != null) {
			List<Building> infirmaries = settlement.getBuildingManager().getBuildings(FunctionType.MEDICAL_CARE);
			if (infirmaries.size() > 0) {
				int rand = RandomUtil.getRandomInt(infirmaries.size() - 1);
				Building foundBuilding = infirmaries.get(rand);
				found = (MedicalAid) foundBuilding.getMedical();// .getFunction(FunctionType.MEDICAL_CARE);
			}
		}

		Vehicle vehicle = getVehicle();
		if (vehicle != null && vehicle instanceof Medical) {
			found = ((Medical) vehicle).getSickBay();
		}

		return found;
	}

	/**
	 * Returns the person's mind
	 *
	 * @return the person's mind
	 */
	public Mind getMind() {
		return mind;
	}

	/**
	 * Returns the person's job name
	 *
	 * @return the person's job name
	 */
	public String getJobName() {
		return mind.getJob().getName(gender);
	}

	/**
	 * Updates and returns the person's age
	 *
	 * @return the person's age
	 */
	public int updateAge() {
		age = earthClock.getYear() - year - 1;
		if (earthClock.getMonth() >= month)
			if (earthClock.getDayOfMonth() >= day)
				age++;

		return age;
	}

	/**
	 * Set a person's age and update one's year of birth
	 *
	 * @param newAge
	 */
	public void changeAge(int newAge) {
		// Back calculate a person's year
		int y = earthClock.getYear() - newAge - 1;
		// Set year to newYear
		year = y;
		age = newAge;
	}

	/**
	 * Returns the person's height in cm
	 *
	 * @return the person's height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Returns the person's birth date in the format of "2055-05-06"
	 *
	 * @return the person's birth date
	 */
	public String getBirthDate() {
		StringBuilder s = new StringBuilder();
		s.append(year).append("-");
		if (month < 10)
			s.append("0").append(month).append("-");
		else
			s.append(month).append("-");
		if (day < 10)
			s.append("0").append(day);
		else
			s.append(day);

		return s.toString();
	}

	/**
	 * Get the LifeSupport system supporting this Person. This may be from the
	 * Settlement, Vehicle or Equipment.
	 *
	 * @return Life support system.
	 */
	private LifeSupportInterface getLifeSupportType() {

		LifeSupportInterface result = null;
		List<LifeSupportInterface> lifeSupportUnits = new ArrayList<LifeSupportInterface>();

		Settlement settlement = getSettlement();
		if (settlement != null) {
			lifeSupportUnits.add(settlement);
		}

		else {
			
			Vehicle vehicle = getVehicle();
			if ((vehicle != null) && (vehicle instanceof LifeSupportInterface)) {

				if (vehicle.isInVehicleInGarage()) { //BuildingManager.getBuilding(vehicle) != null) {
					// if the vehicle is inside a garage
					lifeSupportUnits.add(vehicle.getSettlement());
				}

				else {
					lifeSupportUnits.add((LifeSupportInterface) vehicle);
				}
			}
		}

		// Get all contained units.
		Collection<Unit> units = getInventory().getContainedUnits();
		for (Unit u : units) {
			if (u instanceof LifeSupportInterface)
				lifeSupportUnits.add((LifeSupportInterface) u);
		}

		// Get first life support unit that checks out.
		for (LifeSupportInterface goodUnit : lifeSupportUnits) {
			if (result == null && goodUnit.lifeSupportCheck()) {
				result = goodUnit;
			}
		}

		// If no good units, just get first life support unit.
		if ((result == null) && (lifeSupportUnits.size() > 0)) {
			result = lifeSupportUnits.get(0);
		}

//		System.out.println(name + " in " + getLocationTag().getImmediateLocation() + " is on " + result.toString() + " life support.");
		return result;
	}

	/**
	 * Gets the gender of the person.
	 *
	 * @return the gender
	 */
	public GenderType getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the person.
	 *
	 * @param g the GenderType
	 */
	public void setGender(GenderType g) {
		gender = g;
	}

	/**
	 * Sets the gender of the person.
	 *
	 * @param g the gender String
	 */
	public void setGender(String g) {
		if (g.equalsIgnoreCase("male") || g.equalsIgnoreCase("m"))
			gender = GenderType.valueOfIgnoreCase("male");
		else if (g.equalsIgnoreCase("female") || g.equalsIgnoreCase("f"))
			gender = GenderType.valueOfIgnoreCase("female");
		else
			gender = GenderType.valueOfIgnoreCase("unknown");
	}

	/**
	 * Gets the birthplace of the person
	 *
	 * @return the birthplace
	 * @deprecated TODO internationalize the place of birth for display in user
	 *             interface.
	 */
	public String getBirthplace() {
		return birthplace;
	}

	/**
	 * Gets the person's local group of people (in building or rover)
	 *
	 * @return collection of people in person's location.
	 */
	public Collection<Person> getLocalGroup() {
		Collection<Person> localGroup = new ConcurrentLinkedQueue<Person>();

		if (isInSettlement()) {
			Building building = BuildingManager.getBuilding(this);
			if (building != null) {
				if (building.hasFunction(FunctionType.LIFE_SUPPORT)) {
					LifeSupport lifeSupport = building.getLifeSupport();
					localGroup = new ConcurrentLinkedQueue<Person>(lifeSupport.getOccupants());
				}
			}
		} else if (isInVehicle()) {
			Crewable crewableVehicle = (Crewable) getVehicle();
			localGroup = new ConcurrentLinkedQueue<Person>(crewableVehicle.getCrew());
		}

		if (localGroup.contains(this)) {
			localGroup.remove(this);
		}
		return localGroup;
	}

	/**
	 * Checks if the vehicle operator is fit for operating the vehicle.
	 *
	 * @return true if vehicle operator is fit.
	 */
	public boolean isFitForOperatingVehicle() {
		return !condition.hasSeriousMedicalProblems();
	}

	/**
	 * Gets the name of the vehicle operator
	 *
	 * @return vehicle operator name.
	 */
	public String getOperatorName() {
		return getName();
	}

	/**
	 * Sets the person's name
	 * 
	 * @param name new name
	 */
	public void setName(String newName) {
		if (!name.equals(newName))
			logger.config("Replace the previous commander '" + name + "' with '" + newName + "' in "
					+ unitManager.getSettlementByID(associatedSettlement) + ".");
		this.name = newName;
		super.setName(newName);
		super.setDescription(unitManager.getSettlementByID(associatedSettlement).getName());
	}

	/**
	 * Gets the settlement the person is currently associated with.
	 *
	 * @return associated settlement or null if none.
	 */
	public Settlement getAssociatedSettlement() {
		return unitManager.getSettlementByID(associatedSettlement);
	}

	/**
	 * Sets the associated settlement for a person.
	 *
	 * @param newSettlement the new associated settlement or null if none.
	 */
	public void setAssociatedSettlement(int newSettlement) {

		if (associatedSettlement != newSettlement) {

			int oldSettlement = associatedSettlement;
			associatedSettlement = newSettlement;

			fireUnitUpdate(UnitEventType.ASSOCIATED_SETTLEMENT_EVENT,
					unitManager.getSettlementByID(associatedSettlement));

			if (oldSettlement != -1) {
				unitManager.getSettlementByID(oldSettlement).removePerson(this);
				unitManager.getSettlementByID(oldSettlement)
						.fireUnitUpdate(UnitEventType.REMOVE_ASSOCIATED_PERSON_EVENT, this);
			}

			if (newSettlement != -1) {
				unitManager.getSettlementByID(newSettlement).addPerson(this);
				unitManager.getSettlementByID(newSettlement).fireUnitUpdate(UnitEventType.ADD_ASSOCIATED_PERSON_EVENT,
						this);
			}
		}
	}

	/**
	 * Sets the associated settlement for a person.
	 *
	 * @param settlement
	 */
	public void setBuriedSettlement(int settlement) {
		buriedSettlement = settlement;
	}

	public Settlement getBuriedSettlement() {
		return unitManager.getSettlementByID(buriedSettlement);
	}

	/**
	 * Gets the person's achievement credit for a given scientific field.
	 *
	 * @param science the scientific field.
	 * @return achievement credit.
	 */
	public double getScientificAchievement(ScienceType science) {
		double result = 0D;
		if (scientificAchievement.containsKey(science)) {
			result = scientificAchievement.get(science);
		}
		return result;
	}

	/**
	 * Gets the person's total scientific achievement credit.
	 *
	 * @return achievement credit.
	 */
	public double getTotalScientificAchievement() {
		double result = 0d;
		for (double value : scientificAchievement.values()) {
			result += value;
		}
		return result;
	}

	/**
	 * Add achievement credit to the person in a scientific field.
	 *
	 * @param achievementCredit the achievement credit.
	 * @param science           the scientific field.
	 */
	public void addScientificAchievement(double achievementCredit, ScienceType science) {
		if (scientificAchievement.containsKey(science)) {
			achievementCredit += scientificAchievement.get(science);
		}
		scientificAchievement.put(science, achievementCredit);
	}

	public void setKitchenWithMeal(Cooking kitchen) {
		this.kitchenWithMeal = kitchen;
	}

	public Cooking getKitchenWithMeal() {
		return kitchenWithMeal;
	}

	public void setKitchenWithDessert(PreparingDessert kitchen) {
		this.kitchenWithDessert = kitchen;
	}

	public PreparingDessert getKitchenWithDessert() {
		return kitchenWithDessert;
	}

	/**
	 * Gets the building the person is located at Returns null if outside of a
	 * settlement
	 *
	 * @return building
	 */
	@Override
	public Building getBuildingLocation() {
		return computeCurrentBuilding();
	}

	/**
	 * Computes the building the person is currently located at Returns null if
	 * outside of a settlement
	 *
	 * @return building
	 */
	public Building computeCurrentBuilding() {
//		if (currentBuilding != null) {
//			return currentBuilding;
//		} else if (getLocationStateType() == LocationStateType.INSIDE_SETTLEMENT) {//isInSettlement()) {
//			currentBuilding = getSettlement().getBuildingManager().getBuildingAtPosition(getXLocation(),
//					getYLocation());
//		}
		return currentBuilding;
	}

	/**
	 * Computes the building the person is currently located at Returns null if
	 * outside of a settlement
	 *
	 * @return building
	 */
	public void setCurrentBuilding(Building building) {
		currentBuilding = building;
	}

	@Override
	public String getTaskDescription() {
		return getMind().getTaskManager().getTaskDescription(false);
	}

	@Override
	public void setMission(Mission newMission) {
		getMind().setMission(newMission);
	}

	@Override
	public void setShiftType(ShiftType shiftType) {
		taskSchedule.setShiftType(shiftType);
	}

	public ShiftType getShiftType() {
		return taskSchedule.getShiftType();
	}

	public double getFatigue() {
		return condition.getFatigue();
	}

	public double getStress() {
		return condition.getStress();
	}

	public int[] getPreferredSleepHours() {
		return circadian.getPreferredSleepHours();
	}

	public void updateSleepCycle(int millisols, boolean updateType) {
		circadian.updateSleepCycle(millisols, updateType);
	}

	public Building getQuarters() {
		return quarters;
	}

	public void setQuarters(Building quarters) {
		this.quarters = quarters;
	}

	public Point2D getBed() {
		return bed;
	}

	public void setBed(Point2D bed) {
		this.bed = bed;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String c) {
		this.country = c;
		if (c != null)
			birthplace = EARTH;
		else
			birthplace = MARS;
	}

	public boolean isDeclaredDead() {
		return declaredDead;
	}

	public boolean isBuried() {
		return isBuried;
	}

	// @Override
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle.getIdentifier();
	}

	/**
	 * Get vehicle person is in, null if person is not in vehicle
	 * 
	 * @return the person's vehicle
	 */
	public Vehicle getVehicle() {
		if (vehicle != -1)
			return unitManager.getVehicleByID(vehicle);
		else
			return null;
	}

	public CircadianClock getCircadianClock() {
		return circadian;
	}
	
	/**
	 * Gets the first name of the person
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the last name of the person
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the status of the person
	 * 
	 * @param status
	 */
	public String getStatus() {
//		status = "okay";
		double p = condition.getPerformanceFactor();
		double h = condition.getHunger();
		double e = condition.getHunger();
		double t = condition.getThirst();
		double s = condition.getStress();
		double f = condition.getFatigue();

		String pStr = PhysicalCondition.getPerformanceStatus(p);
		String hStr = PhysicalCondition.getHungerStatus(h, e);
		String tStr = PhysicalCondition.getThirstyStatus(t);
		String sStr = PhysicalCondition.getStressStatus(s);
		String fStr = PhysicalCondition.getFatigueStatus(f);

		return pStr + " in performance, " + sStr + ", " + fStr + ", " + hStr + ", and " + tStr + ".";

//		if (p > .9) {
//			if (h < 150 && t < 100 && s == 0 && f < 100) {
//				status = "terrific";
//			}
//			else if (h < 250 && t < 150 && s < 10) {
//				status = "very well";
//			}
//			else if (h < 450 && t < 350) {
//				status = "reasonably well";
//			}
//		} else if (p > .7) {		
//			if (h < 400 && t < 300) {
//				status = "good";
//			}
//			else if (h < 600 && t < 500) {
//				status = "alright but hungry/thirsty";	
//			}
//		} else if (p > .4) {		
//			if (h < 600 && t < 500) {
//				status = "getting hungry/thirsty";
//			}
//			else if (h < 800 && t < 700) {
//				status = "really hungry/thirsty";	
//			}
//		}
//		else {
//			status = "not feeling well";
//		}
		// return status;
	}

	/**
	 * Return the mission description if a person is on a mission
	 * 
	 * @return description
	 */
	public String getMissionDescription() {
		Mission m = null;
		if (mind.getMission() != null) {
			m = mind.getMission();
			return m.getDescription();
		} else {
			return "None";
		}
	}

	/**
	 * Adds the mission experience score
	 * 
	 * @param id
	 * @param score
	 */
	public void addMissionExperience(int id, double score) {
		if (missionExperiences.containsKey(id)) {
			List<Double> scores = missionExperiences.get(id);
			scores.add(score);
//			missionExperiences.get(id).add(score);
			// Limit the size of each list to 20
			if (scores.size() > 20)
				scores.remove(0);
		} else {
			List<Double> scores = new ArrayList<>();
			scores.add(score);
			missionExperiences.put(id, scores);
		}
	}

//	/**
//	 * Adds the mission experience score
//	 * 
//	 * @param id
//	 * @param score
//	 */
//	public double getMissionExperience(int id) {
//		if (missionExperiences.containsKey(id)) {
//			List<Double> scores = missionExperiences.get(id);
//			return scores.size() + //			scores.add(score);
//			missionExperiences.get(id).add(score);
//		}
//		else {
//			List<Double> scores = new ArrayList<>();
//			scores.add(score);
//			missionExperiences.put(id, scores);
//		}
//	}

	/**
	 * Gets the mission experiences map
	 * 
	 * @return
	 */
	public Map<Integer, List<Double>> getMissionExperiences() {
		return missionExperiences;
	}

	/**
	 * Adds the EVA time
	 * 
	 * @param taskName
	 * @param time
	 */
	public void addEVATime(String taskName, double time) {
		Map<String, Double> map = null;

		if (eVATaskTime.containsKey(solCache)) {
			map = eVATaskTime.get(solCache);
			if (map.containsKey(taskName)) {
				double oldTime = map.get(taskName);
				map.put(taskName, time + oldTime);
			} else {
				map.put(taskName, time);
			}
		} else {
			map = new ConcurrentHashMap<>();
			map.put(taskName, time);
		}

		eVATaskTime.put(solCache, map);
	}

	/**
	 * Gets the map of EVA task time.
	 * 
	 * @return
	 */
	public Map<Integer, Double> getTotalEVATaskTimeBySol() {
		Map<Integer, Double> map = new ConcurrentHashMap<>();

		for (Integer sol : eVATaskTime.keySet()) {
			double sum = 0;

			for (String t : eVATaskTime.get(sol).keySet()) {
				sum += eVATaskTime.get(sol).get(t);
			}

			map.put(sol, sum);
		}

		return map;
	}

	/**
	 * Adds the amount consumed.
	 * 
	 * @param type
	 * @param amount
	 */
	public void addConsumptionTime(int type, double amount) {
		Map<Integer, Double> map = null;

		if (consumption.containsKey(solCache)) {
			map = consumption.get(solCache);
			if (map.containsKey(type)) {
				double oldAmt = map.get(type);
				map.put(type, amount + oldAmt);
			} else {
				map.put(type, amount);
			}
		} else {
			map = new ConcurrentHashMap<>();
			map.put(type, amount);
		}

		consumption.put(solCache, map);
	}

	/**
	 * Gets the total amount consumed
	 * 
	 * @param type
	 * @return
	 */
	public Map<Integer, Double> getTotalConsumptionBySol(int type) {
		Map<Integer, Double> map = new ConcurrentHashMap<>();

		for (Integer sol : consumption.keySet()) {
			for (Integer t : consumption.get(sol).keySet()) {
				if (t == type) {
					map.put(sol, consumption.get(sol).get(t));
				}
			}
		}

		return map;
	}

	/**
	 * Gets the daily average water usage of the last x sols Not: most weight on
	 * yesterday's usage. Least weight on usage from x sols ago
	 * 
	 * @return
	 */
	public double getDailyUsage(int type) {
		Map<Integer, Double> map = getTotalConsumptionBySol(type);

		boolean quit = false;
		int today = solCache;
		int sol = solCache;
		double sum = 0;
		double numSols = 0;
		double cumulativeWeight = 0.75;
		double weight = 1;

		while (!quit) {
			if (map.size() == 0) {
				quit = true;
				return 0;
			}

			else if (map.containsKey(sol)) {
				if (today == sol) {
					// If it's getting the today's average, one may
					// project the full-day usage based on the usage up to this moment
					weight = .25;
					sum = sum + map.get(sol) * 1_000D / marsClock.getMillisol() * weight;
				}

				else {
					sum = sum + map.get(sol) * weight;
				}
			}

			else if (map.containsKey(sol - 1)) {
				sum = sum + map.get(sol - 1) * weight;
				sol--;
			}

			cumulativeWeight = cumulativeWeight + weight;
			weight = (numSols + 1) / (cumulativeWeight + 1);
			numSols++;
			sol--;
			// Get the last x sols only
			if (numSols > MAX_NUM_SOLS)
				quit = true;
		}

		return sum / cumulativeWeight;
	}

	public double getEatingSpeed() {
		return eatingSpeed;
	}

	/**
	 * Gets the average height of a person.
	 */
	public double getAverageHeight() {
		return averageHeight;
	}

	/**
	 * Gets the average weight of a person.
	 */
	public double getAverageWeight() {
		return averageWeight;
	}

	public int getAge() {
		return age;
	}

	/**
	 * Checks if the person is a preconfigured crew member.
	 */
	public boolean isPreConfigured() {
		return preConfigured;
	}

	/**
	 * Set the person as a preconfigured crew member.
	 */
	public void setPreConfigured(boolean value) {
		preConfigured = value;
	}

	/**
	 * Returns a reference to the Person's skill manager
	 * 
	 * @return the person's skill manager
	 */
	public SkillManager getSkillManager() {
		return skillManager;
	}

	/**
	 * Returns the effective integer skill level from a named skill based on
	 * additional modifiers such as fatigue.
	 * 
	 * @param skillType the skill's type
	 * @return the skill's effective level
	 */
	public int getEffectiveSkillLevel(SkillType skillType) {
		// Modify for fatigue, minus 1 skill level for every 1000 points of fatigue.
		return (int) Math.round(getPerformanceRating() * skillManager.getSkillLevel(skillType));
	}

//	public boolean equals(Object obj) {
//		if (this == obj) return true;
//		if (obj == null) return false;
//		if (this.getClass() != obj.getClass()) return false;
//		Person p = (Person) obj;
//		return this.name.equals(p.getName())
//				&& this.gender.equals(p.getGender())
//				&& this.age == p.getAge()
//				&& this.getBirthDate() == p.getBirthDate();
//	}

//	public void updateBuildingPreference(FunctionType type) {
//		if (buildingPreference.isEmpty()) {
//			for (FunctionType ft : FunctionType.getFunctionTypes()) {
//				buildingPreference.put(ft.ordinal(), 1);
//			}
//		}
//		else {
//			int pref = buildingPreference.get(type.ordinal());
//			if (pref <= 99)
//				buildingPreference.put(type.ordinal(), 1 + pref);
//		}
//	}

//	/**
//	 * Gets the Role Prospect score map
//	 * 
//	 * @return
//	 */
//	public Map<RoleType, Double> getRoleProspectScores() {
//		return roleProspectScores;
//	}
//	
//	/**
//	 * Gets a Role Prospect score
//	 * 
//	 * @param index
//	 * @return
//	 */
//	public double getARoleProspectScore(RoleType role) {
//		return roleProspectScores.get(role);
//	}

//	/**
//	 * Reloads instances after loading from a saved sim
//	 * 
//	 * @param {@link MasterClock}
//	 * @param {{@link MarsClock}
//	 */
//	public static void justReloaded(MasterClock c0, MarsClock c1, Simulation s, Mars m, MarsSurface ms, EarthClock e, UnitManager u) {
//		masterClock = c0;
//		marsClock = c1;
//		sim = s;
//		mars = m;
//		marsSurface = ms;
//		earthClock = e;
//		unitManager = u;
//	}

	/**
	 * Is this person outside on the surface of Mars
	 * 
	 * @return true if the person is outside
	 */
	public boolean isOutside() {
		int id = getContainerID();
		if (id == MARS_SURFACE_ID 
				|| (id >= FIRST_EQUIPMENT_ID && id < UNKNOWN_ID))
			return true;
		
		return false;
	}
	
	/**
	 * has this person donned an EVA suit
	 * 
	 * @return true if the person has donned an EVA suit
	 */
	public boolean isOnEVASuit() {
		int id = getContainerID();
		if (id >= MARS_SURFACE_ID && id < UNKNOWN_ID)
			return true;
		
		return false;
	}
	
	@Override
	public void destroy() {
		super.destroy();
//		relax = null;
//		sleep = null;
//		walk = null;
		circadian = null;
//		vehicle = null;
//		associatedVehicle = null;
//		associatedSettlement = null;
//		buriedSettlement = null;
		quarters = null;
//		diningBuilding = null;
		currentBuilding = null;
		condition = null;
		favorite = null;
		taskSchedule = null;
		jobHistory = null;
		role = null;
		preference = null;
		support = null;
		kitchenWithMeal = null;
		kitchenWithDessert = null;
		ra = null;
		bed = null;

		attributes.destroy();
		attributes = null;
		mind.destroy();
		mind = null;
		// condition.destroy();
		condition = null;
		gender = null;

		skillManager.destroy();
		skillManager = null;

		scientificAchievement.clear();
		scientificAchievement = null;
	}

}