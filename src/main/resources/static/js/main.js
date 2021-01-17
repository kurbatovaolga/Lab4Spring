function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

var employeeApi = Vue.resource('/employee{/id}')


Vue.component ('employee-from',{
    props: ['employees','employeeAttr'],
    data:function() {
        return {
            text: '',
            id: ''
        }
    },
    watch:{
        employeeAttr: function(newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>'+
        '<input type="text" placeholder="Введите ФИО" v-model="text"/>'+
        '<input type="button" value="Save" @click="save"/>'+
        '</div>',
    methods: {
        save: function () {
            var employee = {text: this.text};

            if (this.id) {
                employeeApi.update({id: this.id}, employee).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.employees, data.id);
                        this.employees.splice(index, 1, data);
                        this.text = ''
                        this.id = ''
                    })
                )
            } else {
                employeeApi.save({}, employee).then(result =>
                    result.json().then(data => {
                        this.employees.push(data);
                        this.text = ''
                    })
                )
            }
        }
    }
});


Vue.component('employee-row', {
    props: ['employee', 'editMethod', 'employees'],
    template: '<div>' +
        '<i>({{employee.id}})</i> {{employee.text}}' +
        '<span style="position: absolute; right: 0">'+
        '<input type="button" value="Edit" @click = "edit"/>'+
        '<input type="button" value="X" @click="del" />' +
        '</span>'+
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.employee);
        },
        del: function () {
            employeeApi.remove({id: this.employee.id}).then(result => {
                if (result.ok) {
                    this.employees.splice(this.employees.indexOf(this.employee), 1)
                }
            })
        }
    }
});


Vue.component('employees-list', {
    props: ['employees'],
    data: function (){
        return{
            employee: null
        }
    },
    template:
        '<div style="position: relative; width: 400px;">' +
        '<employee-from :employees="employees" :employeeAttr="employee"/>'+
        '<employee-row v-for="employee in employees" :key="employee.id":employee="employee"' +
        ' :editMethod="editMethod" :employees="employees"/>' +
        '</div>',

    methods:{
        editMethod: function(employee) {
            this.employee = employee;
        }
    }
});


var app = new Vue({
    el: '#app',
    template:  '<div>' +
        '<div v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></div>' +
        '<div v-else>' +
        '<div>{{profile.name}}&nbsp;<a href="/logout">Выйти</a></div>' +
        '<employees-list :employees="employees" />' +
        '</div>' +
        '</div>',
    data: {
        employees: frontendData.employees,
        profile: frontendData.profile
    },
    created: function () {
        // employeeApi.get().then(result =>
        //     result.json().then(data=>
        //         data.forEach(employee => this.employees.push(employee))
        //     )
        // )
    },
});