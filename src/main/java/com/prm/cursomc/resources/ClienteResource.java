package com.prm.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prm.cursomc.domain.Cliente;
import com.prm.cursomc.dto.ClienteDTO;
import com.prm.cursomc.dto.ClienteNewDTO;
import com.prm.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		Cliente cliente = clienteService.find(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
		Cliente obj = clienteService.fromDTO(objDto);		
		obj = clienteService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		 
		return ResponseEntity.created(uri).build();
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		Cliente obj = clienteService.fromDTO(objDTO);
		obj.setId(id);
		obj =clienteService.upadte(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		 clienteService.delete(id);	
		
		 return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente>lista =  clienteService.findAll();
		List<ClienteDTO> listaDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		Page<Cliente>lista =  clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listaDTO = lista.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(listaDTO);
	}
	
}
