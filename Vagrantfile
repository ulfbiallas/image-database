# -*- mode: ruby -*-
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = "2"


$script = <<SCRIPT

	yum -y install java-1.7.0-openjdk

	yum -y install mysql
	yum -y install mysql-server
	service mysqld start

	mysql < /vagrant/createDatabase.sql

	cp /vagrant/imagedb.sh /etc/init.d/imagedb

	# change encoding to unix
	cd /etc/init.d
	tr -d '\r' < imagedb > imagedb.new
	rm -f imagedb
	mv imagedb.new imagedb
	chmod 777 imagedb

	service imagedb start

SCRIPT


Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

	config.vm.box = "chef/centos-6.5-i386"
	config.vm.network "forwarded_port", guest: 8080, host: 8080, auto_correct: true
	config.vm.provision "shell", inline: $script

end