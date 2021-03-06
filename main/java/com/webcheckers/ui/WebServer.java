package com.webcheckers.ui;

import static spark.Spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;

import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import spark.TemplateEngine;


/**
 * The server that initializes the set of HTTP request handlers.
 * This defines the <em>web application interface</em> for this
 * WebCheckers application.
 *
 * <p>
 * There are multiple ways in which you can have the client issue a
 * request and the application generate responses to requests. If your team is
 * not careful when designing your approach, you can quickly create a mess
 * where no one can remember how a particular request is issued or the response
 * gets generated. Aim for consistency in your approach for similar
 * activities or requests.
 * </p>
 *
 * <p>Design choices for how the client makes a request include:
 * <ul>
 *     <li>Request URL</li>
 *     <li>HTTP verb for request (GET, POST, PUT, DELETE and so on)</li>
 *     <li><em>Optional:</em> Inclusion of request parameters</li>
 * </ul>
 * </p>
 *
 * <p>Design choices for generating a response to a request include:
 * <ul>
 *     <li>View templates with conditional elements</li>
 *     <li>Use different view templates based on results of executing the client request</li>
 *     <li>Redirecting to a different application URL</li>
 * </ul>
 * </p>
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:ajn3687@rit.edu'>Arthur Nagashima</a>
 * @author <a href='mailto:rwk8144@rit.edu'>Robert Kurdziel</a>
 */
public class WebServer {
  private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

  //
  // Constants
  //

  /**
   * The URL pattern to request the Home page.
   */
  public static final String HOME_URL = "/";
  /**
   * The URL pattern to request the Signin page.
   */
  public static final String SIGN_URL = "/signin";
  /**
   * The URL pattern to request the Signout page.
   */
  public static final String SIGN_OUT_URL = "/signout";
  /**
   * The URL pattern to request the Game page.
   */
  public static final String GAME_URL = "/game";
  /**
   * The URL pattern to request the Start page.
   */
  public static final String START_URL = "/start";
  /**
   * The URL pattern to request the CheckTurn page.
   */
  public static final String CHECKTURN_URL = "/checkTurn";
  /**
   * The URL pattern to request the ValidateMove page.
   */
  public static final String VALID_URL = "/validateMove";
  /**
   * The URL pattern to request the SubmitTurn page.
   */
  public static final String SUBMIT_URL = "/submitTurn";
  /**
   * The URL pattern to request the BackupMove page.
   */
  public static final String BACKUP_URL = "/backupMove";
  /**
   * The URL pattern to request the Resign page.
   */
  public static final String RESIGN_URL = "/resignGame";
  /**
   * The URL pattern to request the EndGame page
   */
  public static final String ENDGAME_URL = "/endGame";
  /**
   * The URL pattern to request the stats page
   */
  public static final String STATS_URL = "/stats";
  /**
   * The URL pattern to request the saveGame page
   */
  public static final String SAVEGAME_URL = "/saveGame";
  /**
   * The URL pattern to request the saveGame page
   */
  public static final String DELETE_URL = "/delete";
  /**
   * The URL pattern to request the spectate page
   */
  public static final String SPECTATE_URL = "/spectate";
  /**
   * The URL pattern to request the checkChange page
   */
  public static final String CHECK_CHANGE_URL = "/checkChange";
  /**
   * The URL pattern to request the Replay page
   */
  public static final String REPLAY_URL = "/replay";
  /**
   * The URL pattern to request the step page
   */
  public static final String STEP_URL = "/step";
  /**
   * The URL pattern to request the Replay page
   */
  public static final String BACK_URL = "/back";


  //
  // Attributes
  //

  private final TemplateEngine templateEngine;
  private final Gson gson;
  private final PlayerLobby playerLobby;
  private final GameLobby gameLobby;
  //private final Player player;

  //
  // Constructor
  //

  /**
   * The constructor for the Web Server.
   *
   * @param templateEngine
   *    The default {@link TemplateEngine} to render page-level HTML views.
   * @param gson
   *    The Google JSON parser object used to render Ajax responses.
   *
   * @throws NullPointerException
   *    If any of the parameters are {@code null}.
   */
  public WebServer(final TemplateEngine templateEngine, final Gson gson, final PlayerLobby playerLobby,
                   final GameLobby gameLobby) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    //
    this.templateEngine = templateEngine;
    this.gson = gson;
    this.playerLobby = playerLobby;
    this.gameLobby = gameLobby;
  }

  //
  // Public methods
  //

  /**
   * Initialize all of the HTTP routes that make up this web application.
   *
   * <p>
   * Initialization of the web server includes defining the location for static
   * files, and defining all routes for processing client requests. The method
   * returns after the web server finishes its initialization.
   * </p>
   */
  public void initialize() {

    // Configuration to serve static files
    staticFileLocation("/public");

    //// Setting any route (or filter) in Spark triggers initialization of the
    //// embedded Jetty web server.

    //// A route is set for a request verb by specifying the path for the
    //// request, and the function callback (request, response) -> {} to
    //// process the request. The order that the routes are defined is
    //// important. The first route (request-path combination) that matches
    //// is the one which is invoked. Additional documentation is at
    //// http://sparkjava.com/documentation.html and in Spark tutorials.

    //// Each route (processing function) will check if the request is valid
    //// from the client that made the request. If it is valid, the route
    //// will extract the relevant data from the request and pass it to the
    //// application object delegated with executing the request. When the
    //// delegate completes execution of the request, the route will create
    //// the parameter map that the response template needs. The data will
    //// either be in the value the delegate returns to the route after
    //// executing the request, or the route will query other application
    //// objects for the data needed.

    //// FreeMarker defines the HTML response using templates. Additional
    //// documentation is at
    //// http://freemarker.org/docs/dgui_quickstart_template.html.
    //// The Spark FreeMarkerEngine lets you pass variable values to the
    //// template via a map. Additional information is in online
    //// tutorials such as
    //// http://benjamindparrish.azurewebsites.net/adding-freemarker-to-java-spark/.

    //// These route definitions are examples. You will define the routes
    //// that are appropriate for the HTTP client interface that you define.
    //// Create separate Route classes to handle each route; this keeps your
    //// code clean; using small classes.

    // Shows the Checkers game Home page.
    get(HOME_URL, new GetHomeRoute(templateEngine, playerLobby, gameLobby));

    // Shows the Checkers game Sign in page.
    get(SIGN_URL, new GetSignInRoute(templateEngine));

    post(SIGN_URL,  new PostSignInRoute(templateEngine, playerLobby));

    // Shows the Checkers game Home page.
    get(SIGN_OUT_URL, new GetSignOutRoute(templateEngine, playerLobby, gameLobby));

    // Shows the game page
    get(GAME_URL, new GetGameRoute(templateEngine, playerLobby,gameLobby));

    post(START_URL, new PostStartRoute(templateEngine, playerLobby, gameLobby));

    post(CHECKTURN_URL, new PostCheckTurnRoute());

    post(VALID_URL, new PostValidateMoveRoute());

    post(SUBMIT_URL, new PostSubmitTurnRoute());

    post(BACKUP_URL, new PostBackUpMoveRoute());

    get(ENDGAME_URL, new GetEndGameRoute(templateEngine, gameLobby));

    get(STATS_URL, new GetStatsRoute(templateEngine));

    post(SAVEGAME_URL, new PostSaveGameRoute(templateEngine));

    post(RESIGN_URL, new PostResignRoute(gameLobby));

    post(DELETE_URL, new PostDeleteRoute());

    post(REPLAY_URL, new PostReplayRoute());

    post(BACK_URL, new PostBackRoute());

    post(STEP_URL, new PostStepRoute());

    post(SPECTATE_URL, new PostSpectateRoute(playerLobby, gameLobby));

    post(CHECK_CHANGE_URL, new PostCheckChangeRoute());

    LOG.config("WebServer is initialized.");
  }

}