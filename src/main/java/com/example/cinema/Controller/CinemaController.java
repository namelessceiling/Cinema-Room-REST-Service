package com.example.cinema.Controller;

import com.example.cinema.Model.Cinema;
import com.example.cinema.Seat;
import com.example.cinema.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CinemaController {
    Cinema cinema = new Cinema(9, 9);

    @GetMapping("/seats")
    public Cinema retrieveSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseTicket(@RequestBody Seat seat) {
        if (seat == null || seat.getRow() < 1 || seat.getRow() > 9 || seat.getColumn() < 1 || seat.getColumn() > 9) {
            return new ResponseEntity(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
        List<Seat> listOfSeats = cinema.getSeats();
        int rowNumber = seat.getRow();
        int columnNumber = seat.getColumn();
        //1 Dimensional ArrayList
        int seatNumber = rowNumber * columnNumber - 1;
        if (listOfSeats.get(seatNumber).isSeatIsAvailable()) {
            listOfSeats.get(seatNumber).setSeatIsAvailable(false);
            Ticket ticket = new Ticket(seat);
            cinema.getPurchasedSeats().put(ticket.getToken(), seat);
            return ResponseEntity.ok(ticket);
        }
        return new ResponseEntity(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestBody String token) {
        /*
        The token is a multiline string when the post request is sent
        {
            "token": "f76a0fd1-b973-4b08-a491-970eddbc32e2"
        }
        Splitting it at ":" and getting the second half of the array turns the string into
         "f76a0fd1-b973-4b08-a491-970eddbc32e2"
        }
        Retrieving the substring at the second index up to the 37th index gives the correct result
        f76a0fd1-b973-4b08-a491-970eddbc32e2
        * */
        token = token.split(":")[1];
        token = token.substring(2, 38);
        Map<String, Seat> purchasedSeats = cinema.getPurchasedSeats();
        if (!purchasedSeats.containsKey(token)) {
            return new ResponseEntity(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
        Seat seatToBeRefunded = purchasedSeats.get(token);
        int row = seatToBeRefunded.getRow();
        int column = seatToBeRefunded.getColumn();
        int seatNumber = row * column - 1;
        List<Seat> seatsInCinema = cinema.getSeats();
        seatsInCinema.get(seatNumber).setSeatIsAvailable(true);
        purchasedSeats.remove(token);
        return ResponseEntity.ok(Map.of("returned_ticket", seatToBeRefunded));
    }

    @PostMapping("/stats")
    public ResponseEntity showStatistics(@RequestParam(required = false) String password) {
        if (password == null) {
            return new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        if (!password.equals("super_secret")) {
            return new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        Map<String, Seat> purchasedSeats = cinema.getPurchasedSeats();
        int income = 0;
        int availableSeats = 81 - purchasedSeats.size();
        for (Seat s : purchasedSeats.values()) {
            income += s.getPrice();
        }
        return ResponseEntity.ok(Map.of("current_income", income, "number_of_available_seats", availableSeats, "number_of_purchased_tickets", purchasedSeats.size()));
    }
}
