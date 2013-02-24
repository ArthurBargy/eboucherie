$(document).ready(function() {
    // Config listes déroulantes
    $(".chosen").chosen({
        no_results_text: "Pas de résultats pour : ",
        allow_single_deselect: true
    });
    // Événement de changement des listes marquées comme exclusives
    $(".exclusive").chosen().change(function() {
        var id = $(this).attr("id");
        // Désélectionne toutes les autres listes avec la classe ".exclusive"
        $(".exclusive").each(function() {
            if (id != $(this).attr("id")) {
                $(this).val("");
                $(this).trigger("liszt:updated");
            }
        });
    });

    // Config calendriers
    $("#debut").datepicker({
        changeMonth: true,
        changeYear: true,
        onClose: function(selectedDate) {
            // Fixer comme la date minimale pour la fin
            $("#fin").datepicker("option", "minDate", selectedDate);
        }
    });
    $("#fin").datepicker({
        changeMonth: true,
        changeYear: true,
        onClose: function(selectedDate) {
            // Fixer comme la date maximale pour le debut
            $("#debut").datepicker("option", "maxDate", selectedDate);
        }
    });
    $(".date").keyup(function(e) {
        if (e.keyCode == 8 || e.keyCode == 46) {
            $.datepicker._clearDate(this);
        }
    });
});
