package com.assessment.Attendance.controllers;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.assessment.Attendance.models.dto.request.AttedanceDTO;
import com.assessment.Attendance.models.entity.Attendance;
import com.assessment.Attendance.models.entity.FileInfo;
import com.assessment.Attendance.services.AttendanceService;
import com.assessment.Attendance.services.EmployeeService;
import com.assessment.Attendance.services.FileStorageService;




@Controller
// @PreAuthorize("hasRole('EMPLOYEE')")
public class FilesController {

  private final Path root = Paths.get("./src/main/resources/static/uploads");
  String urlPath = "http://localhost:9001/files/";
  @Autowired
  FileStorageService storageService;

  @Autowired
  private AttendanceService attendanceService;

  @Autowired
  private EmployeeService employeeService;
  
  @GetMapping("/")
  public String homepage() {
    return "redirect:/dashboard";
  }
  
  @GetMapping("/attendancess/create")
  public String newFile(@ModelAttribute("attendanceDTO") AttedanceDTO attendanceDTO, Model model) {
      // model.addAttribute("attendanceDTO", attendanceDTO);
      model.addAttribute("employees", employeeService.getAll());
      model.addAttribute("isActive", "create_request");

    return "fileupload/upload_form";
  }

  @GetMapping("/attendance/update/{id}")
  public String updateView (@PathVariable int id, @ModelAttribute("attendanceDTO") AttedanceDTO attendanceDTO, Model model) {

      Attendance attendanceById = attendanceService.getById(id);
      attendanceDTO.setDescription(attendanceById.getDescription());  

      // String[] startDateTime = attendanceById.getStart_time().split("T");
      // String startTime = String.join(" ", startDateTime);

      // String[] endDateTime = attendanceById.getStart_time().split("T");
      // String endTime = String.join(" ", endDateTime);

      
      // System.out.println(startTime);
     
      attendanceDTO.setDate(attendanceById.getDate().toString());
      attendanceDTO.setSelfie_photo(attendanceById.getSelfie_photo());
      model.addAttribute("id", id);
      model.addAttribute("attendanceDTO", attendanceDTO);
      model.addAttribute("employees", employeeService.getAll());

    return "fileupload/update-form";
  }
  

  @GetMapping("/attendance/attachment")
  public String getListFiles(Model model) {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    model.addAttribute("files", fileInfos);

    return "fileupload/files";
  }
  
  @PostMapping("/attendance/upload")
  public String uploadFile(@ModelAttribute("attendanceDTO") AttedanceDTO attendanceDTO,
          @RequestParam("input-attachment") MultipartFile attachment) throws IOException{
      
            
      String[] date_attend = attendanceDTO.getDate().split("T");
      String date = String.join(" ", date_attend);

    

      System.out.println(date);
      attendanceDTO.setDate(date);
      System.out.println(attachment.getOriginalFilename());
      
      try {
          Files.copy(attachment.getInputStream(), this.root.resolve(attachment.getOriginalFilename()));
        } catch (Exception e) {
          if (e instanceof FileAlreadyExistsException) {
            throw new RuntimeException("A file of that name already exists.");
          }

          throw new RuntimeException(e.getMessage());
        }
      attendanceDTO.setSelfie_photo(urlPath+attachment.getOriginalFilename());
      System.out.println(urlPath+attachment.getOriginalFilename());
      System.out.println(urlPath);
      
      System.out.println(attendanceDTO);
      attendanceService.create(attendanceDTO);
      
      return "redirect:/attendance";
  }
  //cobaaaaaaaaaaaaaaaaa
  @PutMapping("/attendance/update/{id}")
  public String updateFile(@PathVariable int id, AttedanceDTO attendanceDTO,
          @RequestParam("input-attachment") MultipartFile attachment) throws IOException{
      
      
      String[] date_attend = attendanceDTO.getDate().split("T");
      String date = String.join(" ", date_attend);

      System.out.println(date);
      attendanceDTO.setDate(date);
      System.out.println(attachment.getOriginalFilename());
      
      try {
          Files.copy(attachment.getInputStream(), this.root.resolve(attachment.getOriginalFilename()));
        } catch (Exception e) {
          if (e instanceof FileAlreadyExistsException) {
            throw new RuntimeException("A file of that name already exists.");
          }

          throw new RuntimeException(e.getMessage());
        }
      attendanceDTO.setSelfie_photo(urlPath+attachment.getOriginalFilename());
      System.out.println(urlPath+attachment.getOriginalFilename());
      System.out.println(urlPath);
      
      System.out.println(attendanceDTO);
      attendanceService.update(id, attendanceDTO);
      
      return "redirect:/overtime";
  }

  @GetMapping("/files/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

    @GetMapping("/load/{filename:.+}")
    public ResponseEntity<Resource> loadFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);

        return ResponseEntity.ok().body(file);
    }
  
  @GetMapping("/files/delete/{filename:.+}")
  public String deleteFile(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes) {
    try {
      boolean existed = storageService.delete(filename);

      if (existed) {
        redirectAttributes.addFlashAttribute("message", "Delete the file successfully: " + filename);
      } else {
        redirectAttributes.addFlashAttribute("message", "The file does not exist!");
      }
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message",
          "Could not delete the file: " + filename + ". Error: " + e.getMessage());
    }

    return "redirect:/fileupload/files";
  }    
}
