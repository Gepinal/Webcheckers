package com.webcheckers.ui;

import com.webcheckers.appl.Game;
import com.webcheckers.appl.GameLobby;
import com.webcheckers.appl.MasterEnum;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;
import static spark.Spark.halt;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;


/**
 * Created by wor3835 on 10/11/2017.
 * @author <a href='mailto:gep2494@rit.edu'>George-Edward Pinal</a>
 * @author <a href='mailto:rwk8144@rit.edu'>Robert Kurdziel</a>
 * @author <a href='mailto:ajn3687@rit.edu'>Arthur Nagashima</a>
 */
public class GetSignOutRoute implements Route {

    /**
     * Global variables to keep track of the players, routes, and templates
     */
    private static final Logger LOG = Logger.getLogger(GetSignOutRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    //private final Player player;
    private final GameLobby gameLobby;

    static final String PLAYER_NAME_USED_ATTR = "playerExists";
    static final String VIEW_NAME = "signin.ftl";

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     * @param playerLobby the WebCheckers player lobby
     * @param gameLobby the WebCheckers game lobby
     */
    public GetSignOutRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, GameLobby gameLobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gameLobby = gameLobby;
        //this.player = player;

        LOG.config("GetSignOutRoute is initialized.");
    }

    /**
     * Signs the player out of WebCheckers
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return nothing, the player is redirected to the home screen
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session session = request.session();
        LOG.finer("GetSignOutRoute is invoked.");

        Player player = session.attribute(GetHomeRoute.CUR_PLAYER_ATTR);
        if(player.isInGame()){
            Game game = gameLobby.inGame(player);
            game.endGame(MasterEnum.GameStatus.SIGNOUT, player.getName());
        }

        Game game = session.attribute(GetGameRoute.GAME_ATTR);
        if(game!=null){
            game.removeSpecator(player);
        }

        playerLobby.removePlayer(player);
        
        session.invalidate();
        response.redirect(WebServer.HOME_URL);
        return null;

    }
}