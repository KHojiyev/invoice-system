package uzdeveloper.invoicesystem.service;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.DetailDTO;

import java.util.List;

@Service
public interface DetailService {

    public Response getAllDetails();

    public Response getOneDetail(Integer id);

    public Response addDetail(DetailDTO detailDTO);

    public Response addDetails(List<DetailDTO> detailDTO);

    public Response updateDetail(Integer id, DetailDTO detailDTO);

    public Response deleteDetail(Integer id);
}
