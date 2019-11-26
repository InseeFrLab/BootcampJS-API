package fr.insee.bootcampjs.telephoneback.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.insee.bootcampjs.telephoneback.model.Designation;
import fr.insee.bootcampjs.telephoneback.persistence.DesignationRepository;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RestDesignation {

	@Autowired
	DesignationRepository repoDesignation;

	@RequestMapping(value = "/helloworld", method = RequestMethod.GET)
	public String helloWorld() {
		return "Hello World";
	}

	@RequestMapping(value = "/designation/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Designation> retrieveDesignation(@PathVariable Long id) {
		Designation res = repoDesignation.getOne(id);
		if (res == null) {
			return new ResponseEntity<Designation>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Designation>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/designations", method = RequestMethod.GET, produces = "application/json")
	public List<Designation> listAllDesignations() {
		return repoDesignation.findAllByOrderByIdAsc();
	}

	@RequestMapping(value = "/available-numbers", method = RequestMethod.GET, produces = "application/json")
	public List<String> listAvailablePhoneNumbers() {
		List<Designation> res = repoDesignation.findEnAttente();
		List<String> phones = new ArrayList<String>();
		for (Designation d : res) {
			phones.add(d.getPhoneNumber());
		}
		return phones;
	}

	@RequestMapping(value = "/designation", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Designation> createDesignation(@RequestBody Designation designation) {
		if (repoDesignation.saveAndFlush(designation) != null) {
			return new ResponseEntity<Designation>(designation, HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
		}
	}

	@RequestMapping(value = "/designation/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public Designation updateDesignation(@PathVariable(value = "id") Long id, @RequestBody Designation designation) {

		// On récupére l'entity correspondant à l'id dans le path (et pas celui dans le
		// body)
		Designation des = repoDesignation.getOne(id);

		// On met à jour les valeurs
		des.setPhoneNumber(designation.getPhoneNumber());
		des.setDesignation(designation.getDesignation());
		des.setRedList(designation.getRedList());
		des.setOffice(designation.getOffice());
		des.setObservation(designation.getObservation());

		// On persiste l'entité mise à jour
		Designation udaptedDesignation = repoDesignation.save(des);
		return udaptedDesignation;

	}

	@Value("classpath:export-template.odt")
	private Resource template;
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> exportList() throws IOException, XDocReportException, NoSuchFieldException, IllegalAccessException {
		List<Designation> list = repoDesignation.toExport();
		InputStream is = template.getInputStream();
		IXDocReport report = XDocReportRegistry.getRegistry().loadReport(is, TemplateEngineKind.Freemarker);
		IContext context = report.createContext();
		context.put("list", list);
		File outFile = File.createTempFile("liste", ".pdf");
		OutputStream oFile = new FileOutputStream(outFile);
		Options options = Options.getTo(ConverterTypeTo.PDF);
		report.convert(context, options, oFile);
		try {
			oFile.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		InputStream isToSend = new FileInputStream(outFile);
		InputStreamResource isRToSend = new InputStreamResource(isToSend);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("content-disposition", "attachment; filename=liste.pdf");
		responseHeaders.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
		return new ResponseEntity<InputStreamResource>(isRToSend, responseHeaders, HttpStatus.OK);
	}

}
