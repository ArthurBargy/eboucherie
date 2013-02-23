$(document).ready(function() {
    $(".chosen").chosen({
        no_results_text: "Pas de résultats pour : ",
        allow_single_deselect: true
    });
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
