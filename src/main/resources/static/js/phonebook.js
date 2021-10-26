function Contact(firstName, lastName, phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.checked = false;
    this.shown = true;
}

new Vue({
    el: "#app",
    data: {
        validation: false,
        serverValidation: false,
        firstName: "",
        lastName: "",
        phone: "",
        rows: [],
        serverError: "",
        queryString: ""
    },
    methods: {
        convertContactList: function (contactListFromServer) {
            return contactListFromServer.map(function (contact, i) {
                return {
                    id: contact.id,
                    firstName: contact.firstName,
                    lastName: contact.lastName,
                    phone: contact.phone,
                    checked: false,
                    shown: true,
                    number: i + 1
                };
            });
        },
        addContact: function () {
            if (this.hasError) {
                this.validation = true;
                this.serverValidation = false;
                return;
            }

            var self = this;

            var contact = new Contact(this.firstName, this.lastName, this.phone);
            $.ajax({
                type: "POST",
                url: "/phonebook/add",
                data: JSON.stringify(contact)
            }).done(function () {
                self.serverValidation = false;
            }).fail(function (ajaxRequest) {
                var contactValidation = JSON.parse(ajaxRequest.responseText);
                self.serverError = contactValidation.error;
                self.serverValidation = true;
            }).always(function () {
                self.loadData();
            });

            self.firstName = "";
            self.lastName = "";
            self.phone = "";
            self.validation = false;
        },
        loadData: function () {
            var self = this;

            $.get("/phonebook/get/all").done(function (response) {
                var contactListFormServer = JSON.parse(response);
                self.rows = self.convertContactList(contactListFormServer);
            });
        },
        deleteContact: function (row) {
            var self = this;

            $.post("/phonebook/deleteContact", {id: row.id}).done(function () {
                self.loadData();
            });
        },
        getFilteredContacts: function () {
            var self = this;

            $.get("/phonebook/get/filteredContacts", {'query': self.queryString}).done(function (response) {
                var contactListFormServer = JSON.parse(response);
                self.rows = self.convertContactList(contactListFormServer);
            });
        },
        clearQueryString: function () {
          this.queryString = "";

          this.loadData();
        },
        deleteCheckedContacts: function () {
            var self = this;

            var idsString = "";

            self.rows.forEach(function (row) {
                if (row.checked) {
                    idsString += row.id + ","
                }
            })

            $.post("/phonebook/deleteContacts", {idsString: idsString.substr(0, idsString.length - 1)}).done(function () {
                self.loadData();
            });
        }
    },
    computed: {
        firstNameError: function () {
            if (this.firstName) {
                return {
                    message: "",
                    error: false
                };
            }

            return {
                message: "Поле Имя должно быть заполнено.",
                error: true
            };
        },
        lastNameError: function () {
            if (!this.lastName) {
                return {
                    message: "Поле Фамилия должно быть заполнено.",
                    error: true
                };
            }

            return {
                message: "",
                error: false
            };
        },
        phoneError: function () {
            if (!this.phone) {
                return {
                    message: "Поле Телефон должно быть заполнено.",
                    error: true
                };
            }

            var self = this;

            var sameContact = this.rows.some(function (c) {
                return c.phone === self.phone;
            });

            if (sameContact) {
                return {
                    message: "Номер телефона не должен дублировать другие номера в телефонной книге.",
                    error: true
                };
            }

            return {
                message: "",
                error: false
            };
        },
        hasError: function () {
            return this.lastNameError.error || this.firstNameError.error || this.phoneError.error;
        },
    },
    created: function () {
        this.loadData();
    }
});

