package ui;

import managers.ActorManager;
import managers.DirectorManager;
import model.*;

import java.util.List;
import java.util.Scanner;

public class UI {
    private List<Film> films;

    public UI(List<Film> films){
        this.films = films;
    }

    public void printMenu(){
        System.console().flush();
        Utils.print("\nneco tady, co to musim jeste vymyslet");
    }
    public void printAllFilms(){
        for (Film dat : films){
            String stat = dat instanceof Played ? "Hvezdicek" : "Bodu";
            Utils.print("\n");
            Utils.print(dat.getName());
            Utils.print("\tRok vydani: " + dat.getYearOfRelease());
            Utils.print("\tReziser: " + dat.getDirector().getName() + " " + dat.getDirector().getSurname());
            List<Actor> actors = dat.getActors();
            for (Actor act : actors){
                Utils.print(act.getName() + " " + act.getSurname());
            }
            List<Review> reviews = dat.getReviews();
            for (Review rev : reviews){
                Utils.print("\tstat" + ": " + rev.getPoints());
                Utils.print("\tHodnoceni: " + rev.getReview());
            }
        }
    }

    public Film createFilm() throws Exception {
        Film film = null;
        Utils.print("Vyberte typ filmu: 1. animovany | 2. hrany");
        int in = Utils.intInput();
        if (in == 1){
            film = new Animated();
        } else if (in == 2) {
            film = new Played();
        } else {
            Utils.print("Neplatny vyber, opakujte akci!");
            createFilm();
        }
        Utils.print("Rok vydani filmu: ");
        film.setYearOfRelease(Utils.intInput());
        Utils.print("Jmeno rezisera: ");
        String[] names = Utils.stringInput().split(" ");
        film.getDirector().setName(names[0]);
        film.getDirector().setSurname(names[1]);
        Utils.print("Zadejte umelce (q pro ukonceni zadavani umelcu: ");
        boolean ter = false;
        while (!ter){
            Actor actor = new Actor();
            String name = Utils.stringInput();
            if(!name.equals("q")){
                names = name.split(" ");
                actor.setName(names[0]);
                actor.setSurname(names[1]);
                film.getActors().add(actor);
            } else {
                ter = true;
            }
        }
        if (film instanceof Animated){
            Utils.print("Zadejte doporuceny vek: ");
            ((Animated) film).setRecommendedAge(Utils.intInput());
        }
        return film;
    }

    public void setFilm (Film film, DirectorManager dirmng, ActorManager actmng) throws Exception {
        Played played = null;
        Animated animated = null;
        boolean ter = false;
        System.out.print("Vyberte co chcete zmenit:\n" +
                "1. nazev filmu | " +
                "2. rok vydani | " +
                "3. rezisera | " +
                "4. herce");
        if (film instanceof Animated){Utils.print(" | 5. doporuceny vek");}
        Utils.print("6. konec uprav");
        while(!ter) {
            int choice = Utils.intInput();
            switch (choice){
                case 1:
                    Utils.print("Novy nazev filmu:");
                    film.setName(Utils.stringInput());
                    break;
                case 2:
                    Utils.print("Novy rok vydani:");
                    film.setYearOfRelease(Utils.intInput());
                    break;
                case 3:
                    Utils.print("Nove jmeno rezisera:");
                    String[] names = Utils.stringInput().split(" ");
                    film.setDirector(dirmng.addDirector(names[0], names[1]));
                    break;
                case 4:
                    Utils.print("Vyberte herce k uprave nebo smazani:");
                    List<Actor> actors = film.getActors();
                    for (int i = 0; i <= actors.size(); i++){
                        Utils.print((i+1) + ". " + actors.get(i).getName() + " " + actors.get(i).getSurname());
                    }
                    Utils.print("\nCo chcete s hercem udelat?\n" +
                            "1. pridat\n" +
                            "2. upravit\n" +
                            "3. odebrat");
                    int in = Utils.intInput();
                    switch (in){
                        case 1:
                            Utils.print("Zadejte jmeno herce:");
                            Actor actor = new Actor();
                            String[] name = Utils.stringInput().split(" ");
                            actor.setName(name[0]);
                            actor.setSurname(name[1]);
                            actors.add(actor);
                            break;
                        case 2:
                            Utils.print("Zadejte cislo herce k uprave:");
                            int idx = Utils.intInput() - 1;
                            Utils.print("Zadejte nove jmeno herce:");
                            String[] nm = Utils.stringInput().split(" ");
                            actors.get(idx).setName(nm[0]);
                            actors.get(idx).setSurname(nm[1]);
                            break;
                        case 3:
                            Utils.print("Zadejte cislo herce ke smazani:");
                            int index = Utils.intInput() - 1;
                            actors.remove(index);
                            break;
                        default:
                            Utils.print("Neplatny vyber!");
                            break;
                    }
                case 5:
                    if (film instanceof Played){
                        Utils.print("Neplatny vyber!");
                        break;
                    } else {
                        animated = (Animated) film;
                        Utils.print("Zadejte novy doporuceny vek:");
                        animated.setRecommendedAge(Utils.intInput());
                    }
                    break;
                case 6:
                    ter = true;
                    break;
                default:
                    Utils.print("Neplatny vyber!");
                    break;
            }
        }
        film.setStatus(DBBase.BaseStatus.edited);
    }
}
