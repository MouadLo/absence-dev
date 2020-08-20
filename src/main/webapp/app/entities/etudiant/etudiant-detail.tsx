import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './etudiant.reducer';
import { IEtudiant } from 'app/shared/model/etudiant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEtudiantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EtudiantDetail = (props: IEtudiantDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { etudiantEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="intranetApp.etudiant.detail.title">Etudiant</Translate> [<b>{etudiantEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="login">
              <Translate contentKey="intranetApp.etudiant.login">Login</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.login}</dd>
          <dt>
            <span id="mdp">
              <Translate contentKey="intranetApp.etudiant.mdp">Mdp</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.mdp}</dd>
          <dt>
            <span id="cne">
              <Translate contentKey="intranetApp.etudiant.cne">Cne</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.cne}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="intranetApp.etudiant.email">Email</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.email}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="intranetApp.etudiant.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="intranetApp.etudiant.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.prenom}</dd>
          <dt>
            <span id="tel">
              <Translate contentKey="intranetApp.etudiant.tel">Tel</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.tel}</dd>
          <dt>
            <Translate contentKey="intranetApp.etudiant.classe">Classe</Translate>
          </dt>
          <dd>{etudiantEntity.classe ? etudiantEntity.classe.idClasse : ''}</dd>
        </dl>
        <Button tag={Link} to="/etudiant" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/etudiant/${etudiantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ etudiant }: IRootState) => ({
  etudiantEntity: etudiant.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EtudiantDetail);
