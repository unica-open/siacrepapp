/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
/* ***************************
 ****** Gestione errori ******
 *****************************/
//*
window.onerror = function(msg, url, line) {
	alert("Error: " + msg + "\nURL: " + url + "\nLine #: " + line);
	return true;
};
//*/

/*
 ************************************
 **** Funzioni JavaScript comuni ****
 ************************************
 */

/**
 * Trim dei caratteri vuoti.
 * <br>
 * Il metodo trimAll serve a effettuare un trim, ovvero una cancellazione, di tutti i caratteri vuoti o rappresentanti uno spazio presenti all'inizio e alla fine di una data stringa passata come input.
 *
 * @param sString (String) la stringa passata in ingresso
 * 
 * @returns (String) la stringa senza caratteri vuoti n&eacute; in testa n&eacute; in coda
 */
function trimAll(sString) {
    if ($.type(sString) === "string") {
        $.trim(sString);
    }
    return sString;
}

/**
 * Svuota tutte le dataTables presenti e visibili nella pagina.
 * 
 * @param arrayDaIgnorare (Array) l'array di tabelle da ignorare
 */
function cleanDataTables(arrayDaIgnorare) {
    $.each($.fn.dataTable.fnTables(true), function(key, value) {
        // Controllo se l'elemento non sia nell'array di elementi da ignorare
        if($.inArray(value.id, arrayDaIgnorare) === -1) {
            $(value).dataTable().fnClearTable();
            $(value).dataTable().fnDestroy();
        }
    });
}

/**
 * Parsing di un numero escludendo il locale.
 * <br>
 * Il presente metodo &egrave; utilizzato ogni qual volta si debba parsificare un numero decimale, in quanto differenti locali possono ripercuotersi in differenti metodi di scrittura del numero.
 * In particolare, la versione corrente:
 * <ul>
 *   <li>elimina la virgola come separatore decimale e la sostituisce con il carattere di punto decimale;</li>
 *   <li>elimina il punto come separatore delle terne di cifre</li>
 * </ul>
 * come da definizione dello standard ECMA-262.
 *
 * @param num (String) stringa rappresentante il numero decimale da parsificare
 * 
 * @returns (String) stringa contenente il numero parsificato, tolto il locale
 * 
 * @see Standard ECMA-262, {@plainlink http://www.ecma-international.org/publications/standards/Ecma-262.htm}, p.20
 */
function parseLocalNum(num) {
	return num.replace(/\./g, "").replace(/\,/g, ".");
}

/**
 * Controlla che il numero passato in input sia un intero.
 * <br>
 * Il codice restituisce false ogni qual volta il parametro fornito non &eacute; in formato numerico valido.
 *
 * @param num (Object) il numero da controllare
 * 
 * @returns (Boolean) <code>true</code> se il numero in input &eacute; un intero; <code>false</code> in caso contrario.
 */
function checkIsInteger(num) {
	return $.isNumeric(num) && (parseFloat(num) === parseInt(num, 10));
}

/**
 * Controlla qualora il valore inserito nel campo considerato sia numerico o meno.
 * Nel caso in cui tale valore non sia numerico, ripristina il focus sul campo.
 * <br>
 * La funzione permette di uscire dal loop di focus nel caso in cui il valore sia lasciato vuoto.
 *
 * @param campo         (Object) il campo da considerare
 * @param nomeParametro (String) il nome del parametro corrispondente al campo
 */
function checkIsNumeric(campo, nomeParametro) {
    var campoJQ = $(campo),
        value = campoJQ.val();
    if (value === "") {
        return;
    } 
    if (!($.isNumeric(value))) {
        alert("CRU_ERR_0001 - Il campo " + nomeParametro + " non ha un formato valido");
        setTimeout(
            function () {
                campo.focus();
            },
            1
        );
    }
}

/**
 * Controlla qualora il valore inserito nel campo considerato sia numerico o meno.
 * Nel caso in cui tale valore non sia numerico, ripristina il focus sul campo.
 * Nel caso in cui il valore sia numerico, imposta la precisione di tale valore a due cifre decimali tramite la formattazione a importo.
 * <br>
 * La funzione permette di uscire dal loop di focus nel caso in cui il valore sia lasciato vuoto.
 *
 * @param campo         (Object) il campo da considerare
 * @param nomeParametro (String) il nome del parametro corrispondente al campo
 */
function checkIsFloat(campo, nomeParametro) {
    var campoJQ = $(campo),
        value = campoJQ.val();
    if (value === "") {
        return;
    }
    if (!($.isNumeric(value))) {
        alert("CRU_ERR_0001 - Il campo " + nomeParametro + " non ha un formato valido");
        setTimeout(
            function () {
                campo.focus();
            },
            1
        );
    } else {
        campoJQ.val(parseFloat(value).formatMOney());
    }
}

/**
 * Formattazione dei float, impostando il locale europeo e la precisione a due cifre decimali.
 *
 * @param campo (Object) il campo da considerare
 */
function formattaFloat(campo) {
    var campoJQ = $(campo),
        value = parseLocalNum(campoJQ.val());
    if (value === "") {
        return;
    }
    if ($.isNumeric(value)) {
        campoJQ.val(parseFloat(value).formatMoney());
    }
}

/**
 * Rimpiazza tutte le occorrenze di una data sequenza di caratteri da una stringa.
 *
 * @param find    (String) la sequenza da trovare per essere sostituita
 * @param replace (String) la sequenza che sostituir&agrave; la sequenza da sostituire
 * @param str     (String) la stringa in cui effettuare la sostituzione
 *
 * @returns (String) la stringa di partenza con la sostituzione di ogni sequenza cercata con quella voluta
 *
 * @see http://stackoverflow.com/questions/1144783
 */
function replaceAll(find, replace, str) {
    return str.replace(new RegExp(find, 'g'), replace);
}

/**
 * Crea una funzione per l'invio via POST della chiamata AJAX per recuperare il JSON.
 * <br>
 * Utilizzo:
 * <pre>
 *   $().postJSON(...)
 * </pre>
 *
 * @param url     (String)  l'url cui effettuare la chiamata
 * @param data    (String)  i dati da inviare
 * @param success (String)  la funzione da eseguire in caso di successo
 * @param async   (Boolean) se la chiamata sia asincrona
 * 
 * @returns (Deferred) l'oggetto jQuery associato alla chiamata AJAX
 */
$.fn.postJSON = function(url, data, success, /* Optional */ async) {
	var result,
		asTemp = async,
		successFunction = success,
		dataAjax = data,
		as;
	// Se non ho preimpostato il il parametro data, shifto le variabili di uno indietro
	if($.isFunction(data)) {
		asTemp = async || success;
		successFunction = data;
		dataAjax = undefined;
	}
	
	as = (asTemp === undefined ? true : asTemp);
	
	try {
		result = $.ajax({
			type: "POST",
			url: url,
			data: dataAjax,
			success: successFunction,
			dataType: "json",
			async: as
		}).fail(
    		function(jqXHR) {
    			var stringaErrore = "Errore nella chiamata al servizio: " + jqXHR.status + " - " + jqXHR.statusText;
    			console.error(stringaErrore);
    			if(jqXHR.status !== 0) {
    				alert(stringaErrore);
    			}
    		}
    	);
	} catch(errore) {
		console.error(errore);
		result = $.Deferred().resolve();
	}
    return result;
};

/**
 * Serializes to an object.
 * 
 * @returns (Object) the serialized object
 */
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if(o[this.name] !== undefined) {
            if(!o[this.name].push) {
               o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * Substitutes an handler for an event.
 * 
 * @param eventName (String)   the name of the event
 * @param handler   (Function) the function handler
 */
$.fn.substituteHandler = function(eventName, handler) {
	return this.off(eventName).on(eventName, handler);
};

/**
 * Prevents the default action for an event
 * 
 * @param eventName (String) the name of the event
 */
$.fn.eventPreventDefault = function(eventName) {
	return this.substituteHandler(eventName, function(e) {
		e.preventDefault();
	});
};

/**
 * Gestione del triplo click.
 * 
 * @see http://benalman.com/news/2010/03/jquery-special-events/
 */
(function($){
	// A collection of elements to which the tripleclick event is bound.
	var elems = $([]),
	// Initialize the clicks counter and last-clicked timestamp.
	    clicks = 0,
	    last = 0;

	// Click speed threshold, defaults to 500.
	$.tripleclickThreshold = 500;

	// Special event definition.
	$.event.special.tripleclick = {
		setup: function(){
			// Add this element to the internal collection.
			elems = elems.add( this );
			// If this is the first element to which the event has been bound, bind a handler to document to catch all 'click' events.
			if ( elems.length === 1 ) {
				$(document).bind( 'click', click_handler );
			}
		},
		teardown: function(){
			// Remove this element from the internal collection.
			elems = elems.not( this );

			// If this is the last element removed, remove the document 'click' event handler that "powers" this special event.
			if ( elems.length === 0 ) {
				$(document).unbind( 'click', click_handler );
			}
		}
	};

	// This function is executed every time an element is clicked.
	function click_handler( event ) {
		var elem = $(event.target);

		// If more than 'threshold' time has passed since the last click, reset the clicks counter.
		if ( event.timeStamp - last > $.tripleclickThreshold ) {
			clicks = 0;
		}

		// Update the last-clicked timestamp.
		last = event.timeStamp;

		// Increment the clicks counter. If the counter has reached 3, trigger the "tripleclick" event and reset the clicks counter to 0. Trigger bound handlers using triggerHandler so the event doesn't propagate.
		if ( ++clicks === 3 ) {
			elem.trigger( 'tripleclick' );
			clicks = 0;
		}
	};
}(jQuery));

/**
 * Funzione di utilit&agrave; per l'impostazione dei dati all'interno degli alert
 * 
 * @param arrayDaInjettare (Array)  l'array dei dati da injettare nell'alert
 * @param alertDaPopolare  (jQuery) l'alert da popolare con i valori dell'alert
 * 
 * @returns (Boolean) <code>true</code> se i dati sono stati impostati; <code>false</code> altrimenti
 * 
 */
function impostaDatiNegliAlert(arrayDaInjettare, alertDaPopolare) {
    var result = false;
    if(arrayDaInjettare !== null && arrayDaInjettare !== undefined && arrayDaInjettare.length > 0 && alertDaPopolare.length > 0) {
        alertDaPopolare.children("ul").find("li").remove().end();
        // Aggiungo gli errori alla lista
        $.each(arrayDaInjettare, function(key, value) {
            // Se ho un oggetto con codice e descrizione, lo spezzo; in caso contrario utilizzo direttamente il valore in entrata
            if(this.codice !== undefined) {
                alertDaPopolare.find("ul").append($("<li/>").html(this.codice + " - " + this.descrizione));
            } else {
                alertDaPopolare.find("ul").append($("<li/>").html(value));
            }
        });
        // Mostro l'alert di errore al termine dell'eventuale animazione
        alertDaPopolare.promise()
        	.done(function() {
        		this.slideDown();
        	});
        // Ritorno true per indicare di aver injettato i parametri
        result = true;
        // Sposto l'attenzione della pagina sull'alert
        window.location.hash = alertDaPopolare.attr("id");
    }
    return result;
}

/**
 * Formattazione numerica di ogni elemento avente classe 'formattazioneNumerica'.
 */
function formattazioneNumerica() {
	$(".formattazioneNumerica").each(function() {
    	var self = $(this);
    	var number = parseFloat(self.html());
    	if(!isNaN(number)) {
    		self.html(number.formatMoney());
    	}
    });
}

/**
 * Gestione dei decimali.
 * 
 * @param ignoreFirst (Boolean) ignorare il primo blur
 */
$.fn.gestioneDeiDecimali = function(ignoreFirst) {
	return this.on("blur", function() {
		var self = $(this);
		// Per gestire il rientro a seguito di una formattazione numerica non corretta
		var number;
		if(self.val().indexOf(",") === -1) {
			// Se non ho virgole, allora parsifico il numero in maniera normale
			number = parseFloat(self.val());
		} else {
			// Se ho una virgola, allora il numero dovrebbe essere formattato
			number = parseFloat(self.val().replace(/\./g, "").replace(/,/g, "."));
		}
		if(!isNaN(number)) {
			self.val(number.formatMoney());
		}
	}).each(function() {
		if(!ignoreFirst) {
			// Richiamo immediatamente la funzione di cui sopra
			$(this).blur();
		}
	});
}

/*
 * *****************************************
 * **** Funzioni JavaScript per Struts2 ****
 * *****************************************
 */

/**
 * Qualifies the source object for the injection in Struts2.
 * 
 * @param source        (Object) the object whose fields are to be qualified
 * @param qualification (String) the qualification to use
 * 
 * @returns (Object) a qualified copy of the source object
 */
function qualify(source, qualification) {
    var key, newKey, index, temp, dest = {};
    // Cycle on object properties
    for(key in source) {
        if(source.hasOwnProperty(key) && source[key] !== undefined) {
            if(Object.prototype.toString.call(source[key]) === "[object Array]") {
                // If the property is an array
                for(index in source[key]) {
                    if(source[key].hasOwnProperty(index)) {
                        newKey = qualification + "." + key + "[" + index + "]";
                        dest[newKey] = source[key][index];
                    }
                }
            } else if(Object.prototype.toString.call(source[key]) === "[object Object]") {
                // If the property is an object
                temp = qualify(source[key], qualification + "." + key);
                for(index in temp) {
                    if(temp.hasOwnProperty(index)) {
                    	dest[index] = temp[index];
                    }
                }
            } else {
                // The property is neither an array nor an object
                newKey = qualification + "." + key;
                dest[newKey] = source[key];
            }
        }
    }
    return dest;
}

/**
 * Unqualifies the source object for the injection in Struts2.
 * 
 * @param source (Object) the object whose fields are to be unqualified
 * @param indice (Number) the optional index to which to unqualify (Default: 0)
 * 
 * @returns (Object) an unqualified copy of the source object
 */
function unqualify(source, /* Optional */ index) {
    // Default value for missing or invalid index
	if(isNaN(index) || index < 0) {
    	index = 0;
    }
	var key, newKey, dest = {};
    for(key in source) {
        if(source.hasOwnProperty(key)) {
            newKey = key.split(".").slice(index).join(".");
            dest[newKey] = source[key];
        }
    }
    return dest;
}

/*
 * ********************************************
 * **** Caricamento di funzionalità varie. ****
 * ********************************************
 */
$(
    function () {
        // Previene il reindirizzamento di ogni link avente href="#"
        $('a[href="#"]').click(function (e) {
            e.preventDefault();
        });

        /* **** Google Prettify **** */
        window.prettyPrint && prettyPrint();

        /* **** Bootstrap **** */
        // Tooltip
        $('.tooltip-demo').tooltip({selector : "a[rel=tooltip]"});
        $(".tooltip-test").tooltip();

        // Popover
        $('.popover-test').popover();
        $("a[rel=popover]").popover()
            .click(
                function (e) {
                    e.preventDefault();
                }
            );

        // Tabs
        $('.nav.nav-tabs').tab();
        // Impedisce ai tab-pane che non sono il primo di essere visualizzati
        // ATTENZIONE! CSS3, tralaltro estremamente nuovo. Non tutti i browser lo supportano. Certamente da mettere a posto in futuro
        $('.tab-pane:not(:first)').removeClass("active");

        // Collapse
        $('.collapse').collapse({
            // Evito che il collapse venga aperto immediatamente
            toggle: false
        }).on('hidden', function () {
			$(this).find(':input').prop("tabindex","-1");	
		}).on('shown', function () {
			$(this).find(':input').removeProp("tabindex");
		});
		
        // Per la gestione dei collapse funzionanti come accordion
        // Cfr. http://stackoverflow.com/questions/13665800/
        $(document).on(
        	'click', 
        	'.accordion-toggle', 
        	function(event) {
	        	// Impedisce la propagazione dell'evento
				event.stopPropagation();
				var self = $(this);
				var parent = self.data('parent');
				var actives = parent && $(parent).find('.collapse.in');
				var target;
				// From bootstrap itself
				if (actives && actives.length) {
					hasData = actives.data('collapse');
					actives.collapse('hide');
				}
				target = self.attr('data-target') || (href = self.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, ''); //strip for ie7
				$(target).collapse('toggle');
			}
        );

        // SelectPicker
        $(".selectpicker").selectpicker({title: ""});
        
        /**
         * Shows the overflow as visible.
         * 
         * @param event         {Event}   the event 
         * @param elt           {Element} the element over which to act
         * @param selector      {String}  the selector for the element
         * @param childSelector {String}  the selector for the children of the element
         */
        function showOverflow(event, elt, selector, childSelector) {
        	event.stopPropagation();
        	$(elt).find(childSelector)
					.css("overflow", "visible")
					.end()
				.find(selector)
					.css("overflow", "hidden");
        }
        
        /**
         * Shows the overflow as hidden.
         * 
         * @param event         {Event}   the event 
         * @param elt           {Element} the element over which to act
         * @param childSelector {String}  the selector for the children of the element
         */
        function hideOverflow(event, elt, childSelector) {
        	$(elt).find(childSelector)
				.css("overflow", "hidden");
        }
        
        // Risoluzione del problema dell'overflow
//        $(document).on("shown", ".accordion-group", function(e) {       // Negli accordion
//    		showOverflow(e, this, ".accordion-group", ".accordion-body");
//    	}).on("hidden", ".accordion-group", function(e) {
//    		hideOverflow(e, this, ".accordion-body");
//    	}).on("shown", ".collapse-group", function(e) {                 // Nei collapse
//    		showOverflow(e, this, ".collapse-group", ".collapse-body");
//    	}).on("hidden", ".collapse-group", function(e) {
//    		hideOverflow(e, this, ".collapse-body");
//    	}).on("shown", ".modal", function(e) {                          // Nei modali
//    		showOverflow(e, this, ".modal", ".accordion-body");
//    	}).on("hidden", ".modal", function(e) {
//    		hideOverflow(e, this, ".modal-body");
//    	});
    	
        // DatePicker
        $(".datepicker").datepicker();
        
        // Overlay
        $("[data-overlay]").overlay();
        
        // AllowedChars
        $(".soloNumeri").allowedChars({numeric: true});

        /* 
         * Gestione dell'hide sull'alert. Per ottenere ciò, basta sostituire il 'data-dismiss' con un 'data-hide'.
         * Cfr. http://stackoverflow.com/questions/13550477/
         */
        $("[data-hide]").on(
            "click",
            function(){
               $(this).closest("." + $(this).attr("data-hide")).slideUp();
            }
        );
        
        /* Formattazione corretta dei campi numerici */
        $(".decimale").gestioneDeiDecimali();
        
        // Formattazione degli elementi con classe 'formattazione numerica'
        formattazioneNumerica();
        
        /* **** B-Wizard **** */
        $("#wizard").bwizard();
    }
);

/**
 * Formatta la valuta.
 *
 * @param c (Number) numero di cifre decimali (Default: 2)
 * @param d (String) divisore decimale        (Default: ',')
 * @param t (String) divisore delle triplette (Default: '.')
 * 
 * @returns (String) l'importo formattato
 *
 * @see http://stackoverflow.com/questions/149055
 */
Number.prototype.formatMoney = function (c, d, t) {
    // Numero
    var n = this;
    // Numero di cifre decimali
    var cifre = isNaN(Math.abs(c)) ? 2 : c;
    // Separatore decimale
    var dec = ((d === undefined) ? "," : d);
    // Separatore delle triplette
    var trip = ((t === undefined) ? "." : t);
    // Segno
    var s = n < 0 ? "-" : "";
    // Parte intera del numero
    var i = parseInt(Math.abs(+n || 0).toFixed(cifre), 10) + '';
    var j = i.length > 3 ? (i.length % 3) : 0;
    var k = Math.abs(n) - i;

    return s + (j ? i.substr(0, j) + trip : "") +
    	i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + trip) +
    	(cifre ? dec + Math.abs(k).toFixed(cifre).slice(2) : '');
};

/**
 * Capitalizza la prima lettera di una stringa.
 */
String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1).toLowerCase();
};