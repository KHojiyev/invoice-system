package uzdeveloper.invoicesystem.controller;

import org.springframework.web.bind.annotation.*;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.DetailDTO;
import uzdeveloper.invoicesystem.service.implement.DetailServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/detail")
public class DetailController {

    final DetailServiceImpl detailServiceImpl;

    public DetailController(DetailServiceImpl detailServiceImpl) {
        this.detailServiceImpl = detailServiceImpl;
    }

    @GetMapping
    public Response getAllDetails() {
        return detailServiceImpl.getAllDetails();
    }

    @GetMapping("/{id}")
    public Response getOneDetail(@PathVariable Integer id) {
        return detailServiceImpl.getOneDetail(id);
    }

    @PostMapping("/one")
    public Response addDetail(@RequestBody DetailDTO detailDTO) {
        return detailServiceImpl.addDetail(detailDTO);
    }

    @PostMapping
    public Response addDetails(@RequestBody List<DetailDTO> detailDTO) {
        return detailServiceImpl.addDetails(detailDTO);
    }

    @PutMapping("/{id}")
    public Response updateDetail(@PathVariable Integer id, @RequestBody DetailDTO detailDTO) {
        return detailServiceImpl.updateDetail(id, detailDTO);
    }

    @DeleteMapping
    public Response deleteDetail(@PathVariable Integer id) {
        return detailServiceImpl.deleteDetail(id);
    }

}
